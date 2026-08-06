import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function ChangePassword() {

    const navigate = useNavigate();

    const [oldPassword, setOldPassword] = useState("");

    const [newPassword, setNewPassword] = useState("");

    const [confirmPassword, setConfirmPassword] = useState("");

    const changePassword = async (e) => {

        e.preventDefault();

        if (newPassword !== confirmPassword) {

            alert("New Password and Confirm Password do not match");

            return;
        }

        try {

            await API.post("/auth/change-password", {

                oldPassword,

                newPassword

            });

            alert("Password changed successfully");

            navigate("/dashboard");

        } catch (error) {

            console.log(error);

            if (error.response) {

                alert(error.response.data);

            } else {

                alert("Server not responding");

            }

        }

    };

    return (

        <div className="login-container">

            <h2>Change Password</h2>

            <form onSubmit={changePassword}>

                <input
                    type="password"
                    placeholder="Old Password"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                    required
                />

                <br /><br />

                <input
                    type="password"
                    placeholder="New Password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                />

                <br /><br />

                <input
                    type="password"
                    placeholder="Confirm Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                />

                <br /><br />

                <button type="submit">

                    Change Password

                </button>

            </form>

        </div>

    );

}

export default ChangePassword;