import React, { useEffect, useState } from "react";
import axios from "axios";

export default function EmployeeManagementApp() {
  const [employees, setEmployees] = useState([]);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    department: "",
  });
  const [editId, setEditId] = useState(null);

  const API = "http://localhost:8080/api/employees";

  // Fetch all employees
  const fetchEmployees = async () => {
    try {
      const response = await axios.get(API);
      setEmployees(response.data);
    } catch (err) {
      console.error("Error fetching employees", err);
    }
  };

  // Create or Update employee
  const handleSubmit = async () => {
    try {
      if (editId) {
        // Update
        await axios.put(`${API}/${editId}`, formData);
      } else {
        // Create
        await axios.post(API, formData);
      }
      resetForm();
      fetchEmployees();
    } catch (err) {
      console.error("Error submitting employee", err);
    }
  };

  const resetForm = () => {
    setFormData({
      firstName: "",
      lastName: "",
      email: "",
      department: "",
    });
    setEditId(null);
  };

  const handleEdit = (emp) => {
    setFormData({
      firstName: emp.firstName,
      lastName: emp.lastName,
      email: emp.email,
      department: emp.department,
    });
    setEditId(emp.id);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this employee?")) {
      try {
        await axios.delete(`${API}/${id}`);
        fetchEmployees();
      } catch (err) {
        console.error("Error deleting employee", err);
      }
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  return (
    <div className="p-6 max-w-5xl mx-auto mt-10 border rounded-xl shadow-lg">
      <h1 className="text-2xl font-bold text-center mb-6">
        Employee Management
      </h1>

      {/* Form */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
        <input
          name="firstName"
          value={formData.firstName}
          onChange={handleChange}
          placeholder="First Name"
          className="border p-2 rounded"
        />
        <input
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
          placeholder="Last Name"
          className="border p-2 rounded"
        />
        <input
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Email"
          className="border p-2 rounded"
        />
        <input
          name="department"
          value={formData.department}
          onChange={handleChange}
          placeholder="Department"
          className="border p-2 rounded"
        />
        <div className="md:col-span-2 flex gap-4">
          <button
            onClick={handleSubmit}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            {editId ? "Update" : "Add"}
          </button>
          {editId && (
            <button
              onClick={resetForm}
              className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Cancel
            </button>
          )}
        </div>
      </div>

      {/* Table */}
      <table className="w-full table-auto border border-black text-sm">
        <thead className="bg-gray-100">
          <tr>
            <th className="border border-black px-2 py-1">ID</th>
            <th className="border border-black px-2 py-1">First Name</th>
            <th className="border border-black px-2 py-1">Last Name</th>
            <th className="border border-black px-2 py-1">Email</th>
            <th className="border border-black px-2 py-1">Department</th>
            <th className="border border-black px-2 py-1">Actions</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((emp) => (
            <tr key={emp.id}>
              <td className="border border-black px-2 py-1">{emp.id}</td>
              <td className="border border-black px-2 py-1">{emp.firstName}</td>
              <td className="border border-black px-2 py-1">{emp.lastName}</td>
              <td className="border border-black px-2 py-1">{emp.email}</td>
              <td className="border border-black px-2 py-1">
                {emp.department}
              </td>
              <td className="border border-black px-2 py-1 space-x-2">
                <button
                  onClick={() => handleEdit(emp)}
                  className="bg-yellow-500 text-white px-2 py-1 rounded hover:bg-yellow-600"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(emp.id)}
                  className="bg-red-600 text-white px-2 py-1 rounded hover:bg-red-700"
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
          {employees.length === 0 && (
            <tr>
              <td colSpan="6" className="text-center py-2">
                No employees found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
