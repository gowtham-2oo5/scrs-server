import React from "react";
import { Link } from "react-router-dom";
function FacultyDashboard() {
  return (
    <div className="grid h-screen place-items-center">
      <h1 className="text-4xl font-bold text-gray-800">Faculty Dashboard</h1>
      <Link to="/login">Logout</Link>
    </div>
  );
}

export default FacultyDashboard;
