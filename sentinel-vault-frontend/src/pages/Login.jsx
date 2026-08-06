import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../api/axiosConfig";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async (e) => {

        e.preventDefault();

        try {

            const response = await API.post("/auth/login", {
                email,
                password
            });

            console.log("FULL RESPONSE:", response);
            console.log("RESPONSE DATA:", response.data);
            console.log("TOKEN:", response.data.token);

            alert("Response:\n" + JSON.stringify(response.data));

            if (!response.data || !response.data.token) {
                alert("Token not received from server");
                return;
            }

            localStorage.setItem("token", response.data.token);

            alert("Token Saved:\n" + localStorage.getItem("token"));

            alert("Login Successful");

            navigate("/dashboard");

        } catch (error) {

            console.log("LOGIN ERROR:", error);

            if (error.response) {

                alert(
                    "Server Error:\n" +
                    JSON.stringify(error.response.data)
                );

            } else {

                alert("Unable to connect to server");

            }
        }
    };

    return (

        <div>

            <h2>Login</h2>

            <form onSubmit={login}>

                <input
                    type="email"
                    placeholder="Enter Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <br /><br />

                <input
                    type="password"
                    placeholder="Enter Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <br /><br />

                <button type="submit">
                    Login
                </button>

            </form>

            <br />

            <p>
                Don't have an account?{" "}
                <Link to="/register">
                    Register
                </Link>
            </p>

        </div>

    );
}

export default Login;