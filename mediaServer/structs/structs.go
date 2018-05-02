package structs

type SuccessResponse struct {
	State    int `json:"state"`
	Message  string `json:"message"`
	FileName string  `json:"file_name"`
}

type ErrorResponse struct {
	State   int `json:"state"`
	Message string `json:"message"`
}
