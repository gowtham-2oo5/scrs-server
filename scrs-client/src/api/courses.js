import axios from "axios";
import { getToken } from ".";

// Get all courses
export const getAllCourses = async () => {
  try {
    const res = await axios.get("/api/course/get-all", {
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

// Create a single course
export const createCourse = async (formData) => {
  try {
    const res = await axios.post("/api/course/create", formData, {
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

// Bulk upload courses
export const bulkUploadCourses = async (file) => {
  try {
    const res = await axios.post("/api/course/bulk-upload", file, {
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

// Update L-T-P-S for a course
export const updateLTPS = async (id, L, T, P, S) => {
  try {
    const res = await axios.put(
      `/api/course/update-ltps?id=${id}&L=${L}&T=${T}&P=${P}&S=${S}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
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

// Update course incharge
export const updateCourseIncharge = async (id, empId) => {
  try {
    const res = await axios.patch(
      `/api/course/update-incharge?id=${id}&empId=${empId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
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

// Update course title
export const updateCourseTitle = async (id, title) => {
  try {
    const res = await axios.patch(
      `/api/course/update-title?id=${id}&title=${title}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
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

// Delete a course
export const deleteCourse = async (id) => {
  try {
    const res = await axios.delete(`/api/course/delete?id=${id}`, {
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

// Get target departments for a course
export const getTargetDepartments = async (id) => {
  try {
    const res = await axios.get(`/api/course/target-departments?id=${id}`, {
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

// Get target specializations for a course
export const getTargetSpecializations = async (id) => {
  try {
    const res = await axios.get(`/api/course/target-specializations?id=${id}`, {
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
