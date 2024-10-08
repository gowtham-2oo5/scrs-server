import axios from "axios";

const backendUrl = "http://localhost:8080";

console.log(backendUrl);

export default async function getUsers() {
  return axios.get(backendUrl + "/user/get-all");
}

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`${backendUrl}/auth/login`, credentials);
    return {
      data: response.data,
      status: response.status,
    };
  } catch (error) {
    if (error.response) {
      return {
        data: error.response.data,
        status: error.response.status,
      };
    } else {
      throw error;
    }
  }
};

export const createAdmin = async (formData) => {
  try {
    const response = await axios.post(`${backendUrl}/admin/create`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return {
      data: response.data,
      status: response.status,
    }; // Return the response data
  } catch (error) {
    throw error.response ? error.response.data : error; // Handle and rethrow errors
  }
};
