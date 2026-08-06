import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Share() {

    const navigate = useNavigate();

    const [files, setFiles] = useState([]);

    const [shareLink, setShareLink] = useState("");

    useEffect(() => {
        loadFiles();
    }, []);

    const loadFiles = async () => {

        try {

            const response = await API.get("/files/my-files");

            setFiles(response.data);

        } catch (error) {

            console.error(error);

            alert("Unable to load files.");

        }

    };

    const generateLink = async (fileId) => {

        try {

            const response = await API.post(
                `/share/${fileId}`
            );

            setShareLink(response.data);

            alert("Share link generated successfully.");

        } catch (error) {

            console.error(error);

            alert("Unable to generate share link.");

        }

    };

    const copyLink = () => {

        navigator.clipboard.writeText(shareLink);

        alert("Link copied successfully.");

    };

    return (

        <div style={{ padding: "30px" }}>

            <h2>Secure File Sharing</h2>

            <button
                onClick={() => navigate("/dashboard")}
            >
                Back to Dashboard
            </button>

            <br />
            <br />

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>File Name</th>
                        <th>File Type</th>
                        <th>Action</th>

                    </tr>

                </thead>

                <tbody>

                    {
                        files.map(file => (

                            <tr key={file.id}>

                                <td>{file.fileName}</td>

                                <td>{file.fileType}</td>

                                <td>

                                    <button
                                        onClick={() =>
                                            generateLink(file.id)
                                        }
                                    >
                                        Share
                                    </button>

                                </td>

                            </tr>

                        ))
                    }

                </tbody>

            </table>

            {
                shareLink &&

                <div style={{ marginTop: "30px" }}>

                    <h3>Share Link</h3>

                    <input
                        type="text"
                        value={shareLink}
                        readOnly
                        style={{
                            width: "500px"
                        }}
                    />

                    <button
                        onClick={copyLink}
                    >
                        Copy Link
                    </button>

                </div>

            }

        </div>

    );

}

export default Share;