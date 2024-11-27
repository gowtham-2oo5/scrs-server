import axios from "axios";

export default async function getUsers() {
  return axios.get("/user/get-all");
}

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`/api/auth/login`, credentials);
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
    const response = await axios.post(`/api/admin/create`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return {
      data: response.data,
      status: response.status,
    };
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};

export const verifyGivenOtp = async (otp) => {
  try {
    const response = await axios.post(`/api/auth/verify-otp`, { otp });
    return {
      data: response.data,
      status: response.status,
    };
  } catch (error) {
    throw new Error(error.response?.data?.message || "OTP verification failed");
  }
};

export const createStudent = async (formData) => {
  try {
    const res = await axios.post(`/api/student/create`, formData, {
      headers: {
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

export const getDepts = async () => {
  try {
    const res = await axios.get("/api/dept/get-all");
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

export const addSingleDept = async (formData) => {
  try {
    const res = await axios.post("/api/dept/insert-one", formData);
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

export const bulkUploadDepts = async (file) => {
  try {
    const res = await axios.post("/api/dept/bulk-upload", file, {
      headers: {},
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

export const getAllFaculties = async (page, size) => {
  try {
    const res = await axios.get(`/api/faculty?page=${page}&size=${size}`);
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
