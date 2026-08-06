import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Upload() {

    const navigate = useNavigate();

    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);

    const uploadFile = async () => {

        if (!file) {

            alert("Please select a file");

            return;

        }

        const formData = new FormData();

        formData.append("file", file);

        try {

            setUploading(true);

            const response = await API.post(
                "/files/upload",
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                }
            );

            alert(response.data);

            navigate("/dashboard");

        } catch (error) {

            console.error(error);

            if (error.response?.status === 401) {

                alert("Session expired. Please login again.");

                localStorage.clear();

                navigate("/login");

            } else if (error.response?.status === 403) {

                alert("Access Denied");

            } else {

                alert("File upload failed");

            }

        } finally {

            setUploading(false);

        }

    };

    return (

        <div
            style={{
                width: "450px",
                margin: "60px auto",
                padding: "30px",
                background: "#1e293b",
                color: "white",
                borderRadius: "10px",
                textAlign: "center"
            }}
        >

            <h2>Upload File</h2>

            <input
                type="file"
                onChange={(e) => setFile(e.target.files[0])}
            />

            <br /><br />

            {file && (

                <div>

                    <p><b>File:</b> {file.name}</p>

                    <p><b>Size:</b> {(file.size / 1024).toFixed(2)} KB</p>

                    <p><b>Type:</b> {file.type}</p>

                </div>

            )}

            <br />

            <button
                onClick={uploadFile}
                disabled={uploading}
                style={{
                    padding: "10px 20px",
                    marginRight: "10px",
                    cursor: "pointer"
                }}
            >

                {uploading ? "Uploading..." : "Upload"}

            </button>

            <button
                onClick={() => navigate("/dashboard")}
                style={{
                    padding: "10px 20px",
                    cursor: "pointer"
                }}
            >

                Back

            </button>

        </div>

    );

}

export default Upload;