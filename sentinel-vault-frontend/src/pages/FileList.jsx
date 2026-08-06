import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function FileList() {

    const [files, setFiles] = useState([]);
    const [sharedFiles, setSharedFiles] = useState([]);
    const [shareEmail, setShareEmail] = useState("");

    const token = localStorage.getItem("token");

    useEffect(() => {
        loadFiles();
        loadSharedFiles();
    }, []);

    // Load my files
    const loadFiles = async () => {

        try {

            const res = await axios.get(
                "http://localhost:8081/files/my-files",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setFiles(res.data);

        } catch (error) {

            console.error(error);
            alert("Unable to load files");
        }
    };

    // Load shared files
    const loadSharedFiles = async () => {

        try {

            const res = await axios.get(
                "http://localhost:8081/share/shared-with-me",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setSharedFiles(res.data);

        } catch (error) {

            console.error(error);
        }
    };

    // Download file
    const downloadFile = async (id, fileName) => {

        try {

            const res = await axios.get(
                `http://localhost:8081/files/download/${id}`,
                {
                    responseType: "blob",
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            const url = window.URL.createObjectURL(
                new Blob([res.data])
            );

            const link = document.createElement("a");

            link.href = url;
            link.download = fileName;

            document.body.appendChild(link);

            link.click();

            link.remove();

        } catch (error) {

            console.error(error);
            alert("Download failed");
        }
    };

    // Delete file
    const deleteFile = async (id) => {

        if (!window.confirm("Delete this file?")) {
            return;
        }

        try {

            await axios.delete(
                `http://localhost:8081/files/${id}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            alert("File deleted successfully");

            loadFiles();

        } catch (error) {

            console.error(error);
            alert("Delete failed");
        }
    };

    // Share file
    const shareFile = async (id) => {

        if (shareEmail.trim() === "") {

            alert("Enter receiver email");

            return;
        }

        try {

            const res = await axios.post(
                `http://localhost:8081/share/${id}?email=${shareEmail}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            alert(res.data);

            setShareEmail("");

        } catch (error) {

            console.error(error);
            alert("Sharing failed");
        }
    };

    return (

        <div className="container">

            <h2>My Files</h2>

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>File Name</th>
                        <th>Type</th>
                        <th>Download</th>
                        <th>Delete</th>
                        <th>Share</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        files.length > 0 ?

                            files.map(file => (

                                <tr key={file.id}>

                                    <td>{file.id}</td>
                                    <td>{file.fileName}</td>
                                    <td>{file.fileType}</td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                downloadFile(
                                                    file.id,
                                                    file.fileName
                                                )
                                            }
                                        >
                                            Download
                                        </button>

                                    </td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                deleteFile(file.id)
                                            }
                                        >
                                            Delete
                                        </button>

                                    </td>

                                    <td>

                                        <input
                                            type="email"
                                            placeholder="Receiver Email"
                                            value={shareEmail}
                                            onChange={(e) =>
                                                setShareEmail(e.target.value)
                                            }
                                        />

                                        <button
                                            onClick={() =>
                                                shareFile(file.id)
                                            }
                                        >
                                            Share
                                        </button>

                                    </td>

                                </tr>

                            ))

                            :

                            <tr>

                                <td colSpan="6">
                                    No files found
                                </td>

                            </tr>

                    }

                </tbody>

            </table>

            <br />

            <h2>Files Shared With Me</h2>

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>File Name</th>
                        <th>Type</th>
                        <th>Download</th>

                    </tr>

                </thead>

                <tbody>

                    {

                        sharedFiles.length > 0 ?

                            sharedFiles.map(file => (

                                <tr key={file.id}>

                                    <td>{file.id}</td>
                                    <td>{file.fileName}</td>
                                    <td>{file.fileType}</td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                downloadFile(
                                                    file.id,
                                                    file.fileName
                                                )
                                            }
                                        >
                                            Download
                                        </button>

                                    </td>

                                </tr>

                            ))

                            :

                            <tr>

                                <td colSpan="4">
                                    No shared files
                                </td>

                            </tr>

                    }

                </tbody>

            </table>

            <br />

            <Link to="/dashboard">
                <button>Back to Dashboard</button>
            </Link>

        </div>
    );
}

export default FileList;