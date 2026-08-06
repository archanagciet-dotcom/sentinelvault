import React, { useEffect, useState } from "react";
import API from "../api/axiosConfig";

function Admin() {

    const [users, setUsers] = useState([]);

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers = async () => {

        try {

            const response = await API.get("/admin/users");

            setUsers(response.data);

        } catch (error) {

            console.log(error);

            alert("Unable to load users");

        }

    };

    return (

        <div style={{ padding: "30px" }}>

            <h2>Admin Panel</h2>

            <table border="1" cellPadding="10">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>

                    </tr>

                </thead>

                <tbody>

                    {
                        users.map(user => (

                            <tr key={user.id}>

                                <td>{user.id}</td>

                                <td>{user.name}</td>

                                <td>{user.email}</td>

                            </tr>

                        ))
                    }

                </tbody>

            </table>

        </div>

    );

}

export default Admin;