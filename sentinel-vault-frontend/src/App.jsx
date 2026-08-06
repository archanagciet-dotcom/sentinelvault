import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Upload from "./pages/Upload";
import Share from "./pages/Share";
import Audit from "./pages/Audit";
import Admin from "./pages/Admin";
import Profile from "./pages/Profile";
import ChangePassword from "./pages/ChangePassword";


function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/dashboard"
                    element={<Dashboard />}
                />
                <Route
    path="/change-password"
    element={<ChangePassword />}
/>

                <Route
                    path="/upload"
                    element={<Upload />}
                />
                <Route
    path="/share"
    element={<Share />}
/>
<Route
    path="/audit"
    element={<Audit />}
/>
<Route
    path="/profile"
    element={<Profile />}
/>
<Route
    path="/"
    element={<Login />}
/>
<Route
    path="/admin"
    element={<Admin />}
/>

            </Routes>

        </BrowserRouter>

    );

}


export default App;