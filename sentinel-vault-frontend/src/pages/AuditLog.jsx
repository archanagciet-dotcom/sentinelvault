import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import API from "../api/axiosConfig";

function AuditLog() {

    const [logs, setLogs] = useState([]);

    useEffect(() => {
        loadLogs();
    }, []);

    const loadLogs = async () => {

        try {

            const response = await API.get("/audit/my-logs");

            setLogs(response.data);

        } catch (error) {

            console.error(error);
            alert("Unable to load audit logs");

        }
    };

    return (

        <div className="container">

            <h2>Audit Logs</h2>

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>Action</th>
                        <th>File</th>
                        <th>Date & Time</th>

                    </tr>

                </thead>

                <tbody>

                    {
                        logs.length > 0 ?

                            logs.map(log => (

                                <tr key={log.id}>

                                    <td>{log.id}</td>
                                    <td>{log.action}</td>
                                    <td>{log.fileName}</td>
                                    <td>{log.actionTime}</td>

                                </tr>

                            ))

                            :

                            <tr>

                                <td colSpan="4">
                                    No audit logs found
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

export default AuditLog;