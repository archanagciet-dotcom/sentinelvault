import { useState } from "react";
import axios from "axios";

function FileUpload({ refreshFiles }) {
  const [file, setFile] = useState(null);

  const upload = async () => {
    if (!file) {
      alert("Select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    const token = localStorage.getItem("token");

    try {
      await axios.post(
        "http://localhost:8081/files/upload",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      alert("File Uploaded");

      setFile(null);

      refreshFiles();
    } catch (err) {
      alert("Upload Failed");
      console.log(err);
    }
  };

  return (
    <div>
      <h3>Upload File</h3>

      <input
        type="file"
        onChange={(e) => setFile(e.target.files[0])}
      />

      <br />
      <br />

      <button onClick={upload}>
        Upload
      </button>
    </div>
  );
}

export default FileUpload;