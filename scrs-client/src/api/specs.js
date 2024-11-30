import axios from "axios";
import { getToken } from ".";

export const getAllSpecs = async () => {
  try {
    const res = await axios.get("/api/spec/get-all", {
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

export const addSingleSpec = async (formData) => {
  try {
    const res = await axios.post("/api/spec/insert-one", formData, {
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

export const bulkUploadSpecs = async (file) => {
  try {
    const res = await axios.post("/api/spec/bulk-upload", file, {
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

export const deleteSpec = async (id) => {
  try {
    const res = await axios.delete(`/api/spec/delete/${id}`, {
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

export const updateSpecDept = async (sn, deptSn) => {
  try {
    console.log("Received sn and deptSn:", sn, deptSn);
    const res = await axios.put(`/api/spec/update/${sn}?deptSn=${deptSn}`, {
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

export const getSpecBySn = async (sn) => {
  try {
    const res = await axios.get(`/api/spec/get/${sn}`, {
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
