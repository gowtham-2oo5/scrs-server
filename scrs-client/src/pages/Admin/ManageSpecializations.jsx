"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Label } from "@/components/ui/label";
import CsvTable from "@/components/CsvTable";
import { Pencil, Trash2, ChevronLeft, ChevronRight } from "lucide-react";
import axios from "axios";

export default function SpecializationManagement() {
  const [specializations, setSpecializations] = useState([
    {
      id: 1,
      name: "Software Engineering",
      sn: "SE001",
      dept: "IT",
      studentCount: 120,
    },
    { id: 2, name: "Data Science", sn: "DS001", dept: "IT", studentCount: 85 },
    { id: 3, name: "Cybersecurity", sn: "CS001", dept: "IT", studentCount: 65 },
    {
      id: 4,
      name: "Digital Marketing",
      sn: "DM001",
      dept: "Marketing",
      studentCount: 95,
    },
    {
      id: 5,
      name: "Financial Analysis",
      sn: "FA001",
      dept: "Finance",
      studentCount: 75,
    },
    {
      id: 6,
      name: "Supply Chain Management",
      sn: "SCM001",
      dept: "Operations",
      studentCount: 55,
    },
    {
      id: 7,
      name: "Customer Experience",
      sn: "CX001",
      dept: "Customer Service",
      studentCount: 40,
    },
  ]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [isReviewOpen, setIsReviewOpen] = useState(false);
  const [csvData, setCsvData] = useState(null);
  const [newSpecialization, setNewSpecialization] = useState({
    id: null,
    name: "",
    sn: "",
    dept: "",
    studentCount: 0,
  });
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const deptOptions = [
    { value: "it", label: "IT" },
    { value: "marketing", label: "Marketing" },
    { value: "finance", label: "Finance" },
    { value: "operations", label: "Operations" },
    { value: "customer_service", label: "Customer Service" },
  ];

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewSpecialization((prev) => ({ ...prev, [id]: value }));
  };

  const handleDeptChange = (value) => {
    setNewSpecialization((prev) => ({ ...prev, dept: value }));
  };

  const handleAddSpecialization = () => {
    if (newSpecialization.id) {
      setSpecializations((prev) =>
        prev.map((spec) =>
          spec.id === newSpecialization.id ? newSpecialization : spec
        )
      );
    } else {
      setSpecializations((prev) => [
        ...prev,
        { ...newSpecialization, id: prev.length + 1, studentCount: 0 },
      ]);
    }
    setNewSpecialization({
      id: null,
      name: "",
      sn: "",
      dept: "",
      studentCount: 0,
    });
    setIsAddModalOpen(false);
    setIsEditModalOpen(false);
  };

  const handleEditSpecialization = (spec) => {
    setNewSpecialization(spec);
    setIsEditModalOpen(true);
  };

  const handleDeleteSpecialization = (id) => {
    setSpecializations((prev) => prev.filter((spec) => spec.id !== id));
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const content = e.target.result;
        setCsvData(content);
        setIsBulkUploadOpen(false);
        setIsReviewOpen(true);
      };
      reader.readAsText(file);
    }
  };

  const handleFileSubmit = async () => {
    try {
      // console.log(csvData);
      // await axios.post("http://localhost:8080/dept/bulk-upload", csvData, {
      //   headers: {
      //     "Content-Type": "multipart/form-data",
      //   },
      // }); // Send CSV data in the request body
      setIsReviewOpen(false); // Close review modal
      alert("Data uploaded successfully");
    } catch (error) {
      console.error("Error uploading data:", error);
      alert("Failed to upload data");
    }
  };

  const SpecializationForm = ({ isEdit = false }) => (
    <div className="grid gap-6 py-4">
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="name" className="font-semibold text-right">
          Name
        </Label>
        <Input
          id="name"
          value={newSpecialization.name}
          onChange={handleInputChange}
          className="col-span-3 rounded-lg"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="sn" className="font-semibold text-right">
          SN
        </Label>
        <Input
          id="sn"
          value={newSpecialization.sn}
          onChange={handleInputChange}
          className="col-span-3 rounded-lg"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="dept" className="font-semibold text-right">
          Department
        </Label>
        <Select onValueChange={handleDeptChange} value={newSpecialization.dept}>
          <SelectTrigger className="col-span-3 rounded-lg">
            <SelectValue placeholder="Select Department" />
          </SelectTrigger>
          <SelectContent className="bg-blue-50 ">
            {deptOptions.map((option) => (
              <SelectItem
                key={option.value}
                value={option.value}
                className="text-blue-800 hover:bg-blue-100"
              >
                {option.label}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <Button
        onClick={handleAddSpecialization}
        className="px-6 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md "
      >
        {isEdit ? "Update Specialization" : "Add Specialization"}
      </Button>
    </div>
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = specializations.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(specializations.length / itemsPerPage);

  return (
    <div className="container p-4 mx-auto sm:p-6 ">
      <h1 className="mb-6 text-2xl font-bold ">Manage Specializations</h1>

      <div className="flex flex-col justify-end mb-6 space-y-2 sm:flex-row sm:space-y-0 sm:space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="w-full px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md  sm:w-auto">
              Add
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg  rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold ">
                Add New Specialization
              </DialogTitle>
            </DialogHeader>
            <SpecializationForm />
          </DialogContent>
        </Dialog>

        <Dialog open={isBulkUploadOpen} onOpenChange={setIsBulkUploadOpen}>
          <DialogTrigger asChild>
            <Button
              variant="outline"
              className="w-full px-4 py-2 font-semibold transition duration-300 ease-in-out border-2 rounded-lg shadow-md sm:w-auto"
            >
              Bulk Upload
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg  rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold ">
                Upload CSV File
              </DialogTitle>
            </DialogHeader>
            <Input
              type="file"
              accept=".csv"
              onChange={handleFileUpload}
              className="rounded-lg "
            />
          </DialogContent>
        </Dialog>
      </div>

      <div className="overflow-hidden border rounded-lg shadow-md">
        <Table className="min-w-full divide-y">
          <TableHeader className="font-bold ">
            <TableRow>
              <TableCell className="px-4 py-2 ">ID</TableCell>
              <TableCell className="px-4 py-2 ">Name</TableCell>
              <TableCell className="px-4 py-2 ">SN</TableCell>
              <TableCell className="px-4 py-2 ">Dept</TableCell>
              <TableCell className="px-4 py-2 ">
                Registered Student Count
              </TableCell>
              <TableCell className="px-4 py-2 ">Actions</TableCell>
            </TableRow>
          </TableHeader>
          <TableBody>
            {currentItems.map((spec) => (
              <TableRow
                key={spec.id}
                className="transition-colors duration-200font-medium"
              >
                <TableCell className="px-4 py-2">{spec.id}</TableCell>
                <TableCell className="px-4 py-2">{spec.name}</TableCell>
                <TableCell className="px-4 py-2">{spec.sn}</TableCell>
                <TableCell className="px-4 py-2">{spec.dept}</TableCell>
                <TableCell className="px-4 py-2">{spec.studentCount}</TableCell>
                <TableCell className="px-4 py-2">
                  <div className="flex space-x-2">
                    <Button
                      onClick={() => handleEditSpecialization(spec)}
                      className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                      variant="outline"
                    >
                      <Pencil className="w-4 h-4" />
                      <span>Edit</span>
                    </Button>
                    <Button
                      onClick={() => handleDeleteSpecialization(spec.id)}
                      variant="destructive"
                      className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                    >
                      <Trash2 className="w-4 h-4" />
                      <span>Delete</span>
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>

      <div className="flex items-center justify-between mt-4">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md "
        >
          <ChevronLeft className="w-4 h-4 mr-2" />
          Previous
        </Button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <Button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages}
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md "
        >
          Next
          <ChevronRight className="w-4 h-4 ml-2" />
        </Button>
      </div>

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent className="border-2 shadow-lg bg-blue-50 rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold ">
              Edit Specialization
            </DialogTitle>
          </DialogHeader>
          <SpecializationForm isEdit={true} />
        </DialogContent>
      </Dialog>

      <Dialog open={isReviewOpen} onOpenChange={setIsReviewOpen}>
        <DialogContent className="border-2 shadow-lg rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Review CSV Data
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4 overflow-scroll">
            <p className="font-semibold ">Review the data from the CSV file:</p>
            <pre className="p-4 mt-2 overflow-auto font-mono text-sm rounded-lg  max-h-60">
              <CsvTable csvData={csvData} />
            </pre>
          </div>
          <Button
            onClick={() => handleFileSubmit()}
            className="px-6 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
          >
            Upload Data
          </Button>
        </DialogContent>
      </Dialog>
    </div>
  );
}
