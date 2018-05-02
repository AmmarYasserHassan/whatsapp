package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"mediaServer/structs"
	"mediaServer/utiles"
	"mime"
	"net/http"
	"os"
	"path/filepath"
)

var uploadPath string = utiles.GetEnv("FOLDER_DIR", "./files")

func main() {
	http.HandleFunc("/upload", uploadFileHandler())

	fs := http.FileServer(http.Dir(uploadPath))
	http.Handle("/files/", http.StripPrefix("/files", fs))

	log.Print("Server started on localhost:5001, use /upload for uploading files and /files/{fileName} for downloading")
	log.Fatal(http.ListenAndServe(":5001", nil))
}

func uploadFileHandler() http.HandlerFunc {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {

		// parse and validate file and post parameters
		file, _, err := r.FormFile("files")
		if err != nil {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "File not sent with the request",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}
		defer file.Close()
		fileBytes, err := ioutil.ReadAll(file)
		if err != nil {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "Invalid file",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}

		// check file type, detect content type only needs the first 512 bytes
		fileType := http.DetectContentType(fileBytes)
		if fileType != "image/jpeg" && fileType != "image/jpg" &&
			fileType != "image/gif" && fileType != "image/png" {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "Invalid file type",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}
		// generate the filename and get the original file type
		fileName := utiles.RandToken(12)
		fileEndings, err := mime.ExtensionsByType(fileType)
		log.Println(fileEndings)
		if err != nil {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "cannot read file type",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}
		newPath := filepath.Join(uploadPath, fileName+fileEndings[0])

		// write file
		newFile, err := os.Create(newPath)
		if err != nil {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "cannot write file",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}
		defer newFile.Close()
		if _, err := newFile.Write(fileBytes); err != nil {
			errorResponse := structs.ErrorResponse{
				State:   400,
				Message: "cannot write file",
			}
			b, _ := json.Marshal(errorResponse)
			utiles.RenderJsonResponse(w, b, http.StatusBadRequest)
			return
		}

		successResponse := structs.SuccessResponse{
			State:    200,
			Message:  "file uploaded successfully",
			FileName: fileName + fileEndings[0],
		}
		b, _ := json.Marshal(successResponse)
		utiles.RenderJsonResponse(w, b, http.StatusOK)
	})
}
