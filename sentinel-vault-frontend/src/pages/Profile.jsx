import React, { useEffect, useState } from "react";
import API from "../api/axiosConfig";

function Profile() {

    const [user, setUser] = useState({});

    useEffect(() => {
        loadProfile();
    }, []);

    const loadProfile = async () => {

        try {

            const response = await API.get("/profile");

            setUser(response.data);

        } catch (error) {

            console.log(error);

            alert("Unable to load profile");

        }

    };

    return (

        <div style={{ padding: "30px" }}>

            <h2>My Profile</h2>

            <table border="1" cellPadding="10">

                <tbody>

                    <tr>
                        <td><b>Name</b></td>
                        <td>{user.name}</td>
                    </tr>

                    <tr>
                        <td><b>Email</b></td>
                        <td>{user.email}</td>
                    </tr>

                    <tr>
                        <td><b>User ID</b></td>
                        <td>{user.id}</td>
                    </tr>

                </tbody>

            </table>

        </div>

    );
}

export default Profile;
