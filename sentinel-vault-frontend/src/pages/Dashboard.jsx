import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";
import "../components/Dashboard.css";

function Dashboard() {

    const navigate = useNavigate();

    const [files, setFiles] = useState([]);

    const username = localStorage.getItem("username") || "User";

    useEffect(() => {

        loadFiles();

    }, []);

    const loadFiles = async () => {

        const token = localStorage.getItem("token");

        if (!token) {

            navigate("/login");

            return;

        }

        try {

            const response = await API.get("/files/my-files");

            setFiles(response.data);

        } catch (error) {

            console.log(error);

            if (
                error.response?.status === 401 ||
                error.response?.status === 403
            ) {

                alert("Session expired");

                localStorage.clear();

                navigate("/login");

            }

        }

    };

    const deleteFile = async (id) => {

        if (!window.confirm("Delete this file?")) {

            return;

        }

        try {

            await API.delete(`/files/${id}`);

            alert("File deleted successfully");

            loadFiles();

        } catch (error) {

            alert("Delete failed");

        }

    };

    // ==========================
    // Preview File
    // ==========================

    const previewFile = async (id) => {

        try {

            const response = await API.get(

                `/files/preview/${id}`,

                {

                    responseType: "blob"

                }

            );

            const url = window.URL.createObjectURL(response.data);

            window.open(url, "_blank");

        } catch (error) {

            console.log(error);

            alert("Preview failed");

        }

    };

    // ==========================
    // Download File
    // ==========================

    const downloadFile = async (id, fileName) => {

        try {

            const response = await API.get(

                `/files/download/${id}`,

                {

                    responseType: "blob"

                }

            );

            const url = window.URL.createObjectURL(response.data);

            const link = document.createElement("a");

            link.href = url;

            link.download = fileName;

            document.body.appendChild(link);

            link.click();

            document.body.removeChild(link);

            window.URL.revokeObjectURL(url);

        } catch (error) {

            console.log(error);

            alert("Download failed");

        }

    };

    const logout = () => {

        localStorage.clear();

        navigate("/login");

    };

    return (

        <div className="dashboard-container">

            <div className="sidebar">

                <h2>Sentinel Vault</h2>

                <button onClick={() => navigate("/dashboard")}>
                    Dashboard
                </button>

                <button onClick={() => navigate("/upload")}>
                    Upload File
                </button>

                <button onClick={loadFiles}>
                    Refresh Files
                </button>

                <button onClick={() => navigate("/profile")}>
                    Profile
                </button>

                <button onClick={() => navigate("/share")}>
                    Share
                </button>

                <button onClick={() => navigate("/audit")}>
                    Audit Logs
                </button>

                <button onClick={() => navigate("/admin")}>
                    Admin
                </button>

                <button onClick={() => navigate("/change-password")}>
                    Change Password
                </button>

                <button
                    className="logout-btn"
                    onClick={logout}
                >
                    Logout
                </button>

            </div>

            <div className="main-content">

                <div className="navbar">

                    <h1>Welcome {username}</h1>

                </div>

                <div className="cards">

                    <div className="card">

                        <h3>Total Files</h3>

                        <p>{files.length}</p>

                    </div>

                    <div className="card">

                        <h3>Storage</h3>

                        <p>Secure</p>

                    </div>

                    <div className="card">

                        <h3>Sharing</h3>

                        <p>Active</p>

                    </div>

                    <div className="card">

                        <h3>Security</h3>

                        <p>ON</p>

                    </div>

                </div>

                <h2 style={{ marginTop: "30px" }}>

                    My Files

                </h2>

                <table className="file-table">

                    <thead>

                        <tr>

                            <th>File Name</th>

                            <th>Type</th>

                            <th>Upload Date</th>

                            <th>Actions</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            files.length === 0 ?

                                (

                                    <tr>

                                        <td colSpan="4">

                                            No files uploaded

                                        </td>

                                    </tr>

                                )

                                :

                                files.map((file) => (

                                    <tr key={file.id}>

                                        <td>{file.fileName}</td>

                                        <td>{file.fileType}</td>

                                        <td>{file.uploadDate}</td>

                                        <td>

                                            <button
                                                className="primary-btn"
                                                onClick={() =>
                                                    previewFile(file.id)
                                                }
                                            >
                                                Preview
                                            </button>

                                            {" "}

                                            <button
                                                className="primary-btn"
                                                onClick={() =>
                                                    downloadFile(
                                                        file.id,
                                                        file.fileName
                                                    )
                                                }
                                            >
                                                Download
                                            </button>

                                            {" "}

                                            <button
                                                className="primary-btn"
                                                onClick={() =>
                                                    navigate(`/share/${file.id}`)
                                                }
                                            >
                                                Share
                                            </button>

                                            {" "}

                                            <button
                                                className="logout-btn"
                                                onClick={() =>
                                                    deleteFile(file.id)
                                                }
                                            >
                                                Delete
                                            </button>

                                        </td>

                                    </tr>

                                ))

                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default Dashboard;