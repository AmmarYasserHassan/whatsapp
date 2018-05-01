import os
from flask import Flask, request, jsonify, send_from_directory
import random
import string

# initialize app constats
UPLOAD_FOLDER = os.getenv('FOLDER_DIR', './files')
ALLOWED_EXTENSIONS = set(['jpeg', 'JPEG','png','PNG', 'mp4','MP4', 'jpg','JPG'])

# create new flask app
app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

"""
    Helper function to check the allowed files
"""
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS


"""
    Helper function to generate random filename
"""
def random_string_generator(size=20, chars=string.ascii_lowercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))


"""
    Upload a file endpoint
"""
@app.route("/file", methods=['POST'])
def upload():
    if 'file' not in request.files:
        resp = jsonify({"state": 400, "message": "no file sent with the request"})
        resp.status_code = 400
        return resp
    else:  
        file = request.files['file']  
        if file and allowed_file(file.filename):
            filename = random_string_generator()
            file.save(os.path.join(
                app.config['UPLOAD_FOLDER'], filename+"."+file.filename.rsplit('.', 1)[1]))
            resp = jsonify({"state": 200, "message": "file uploaded successfully", "filename": filename+"."+file.filename.rsplit('.', 1)[1]})
            resp.status_code = 200
            return resp
        else:
            resp = jsonify({"state": 400, "message": "file extension is not allowed"})
            resp.status_code = 400
            return resp


"""
    Download a file endpoint
"""
@app.route("/file/", methods=['GET'])
def download():
    try:
        return send_from_directory(app.config['UPLOAD_FOLDER'], request.args.get("filename"))
    except :
        resp = jsonify({"state": 404, "message": "file not found"})
        resp.status_code = 404
        return resp
    
"""
    Run the flask app
"""
if __name__ == "__main__":
    print("mediaServer started successfully on port 5001 ")
    app.run(host='0.0.0.0', port=5001, debug=True)
