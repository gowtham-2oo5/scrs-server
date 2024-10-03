import axios from "axios";

const backendUrl = "http://localhost:8080";

console.log(backendUrl);

export default async function getUsers() {
  return axios.get(backendUrl + "/user/get-all");
}

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`${backendUrl}/auth/login`, credentials);
    return response.data; // Return the response data
  } catch (error) {
    throw error.response ? error.response.data : error; // Handle and rethrow errors
  }
};