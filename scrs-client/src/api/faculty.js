import axios from "axios";
import { getToken } from ".";

export const getAllFaculties = async () => {
  try {
    const res = await axios.get(`/api/faculty`, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    return {
      data: res.data,
      status: res.status,
    };
  } catch (error) {
    return {
      data: null,
      error: error.response ? error.response.data : error.message,
    };
  }
};

export const getFacultiesByDept = async (deptName) => {
  try {
    const res = await axios.get(`/api/faculty/get-by-dept`, {
      params: {
        sn: deptName,
      },
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    return {
      data: res.data,
      status: res.status,
    };
  } catch (error) {
    return {
      data: null,
      error: error.response ? error.response.data : error.message,
    };
  }
};

export const createNewFaculty = async (formData) => {
  try {
    const res = await axios.post(`/api/faculty/create`, formData, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "multipart/form-data",
      },
    });
    return {
      data: res.data,
      status: res.status,
    };
  } catch (error) {
    return {
      data: null,
      error: error.response ? error.response.data : error.message,
    };
  }
};
