import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../api/axiosConfig";

function Register() {

    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const register = async (e) => {

        e.preventDefault();

        try {

            const response = await API.post("/auth/register", {

                name,
                email,
                password

            });

            alert(response.data);

            setName("");
            setEmail("");
            setPassword("");

            navigate("/");

        } catch (error) {

            if (error.response) {

                alert(error.response.data.message || error.response.data);

            } else {

                alert("Unable to connect to server");

            }

        }

    };

    return (

        <div>

            <h2>Register</h2>

            <form onSubmit={register}>

                <div>

                    <input
                        type="text"
                        placeholder="Enter Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

                </div>

                <br />

                <div>

                    <input
                        type="email"
                        placeholder="Enter Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                </div>

                <br />

                <div>

                    <input
                        type="password"
                        placeholder="Enter Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                </div>

                <br />

                <button type="submit">

                    Register

                </button>

            </form>

            <br />

            <p>

                Already have an account?

                <Link to="/">

                    Login

                </Link>

            </p>

        </div>

    );

}

export default Register;