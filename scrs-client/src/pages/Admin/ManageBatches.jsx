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
import { Pencil, Trash2, ChevronLeft, ChevronRight } from "lucide-react";

export default function BatchManagement() {
  const [batches, setBatches] = useState([
    { id: 1, name: "Batch 2023", currentYear: 1, totalStrength: 150 },
    { id: 2, name: "Batch 2022", currentYear: 2, totalStrength: 140 },
    { id: 3, name: "Batch 2021", currentYear: 3, totalStrength: 135 },
    { id: 4, name: "Batch 2020", currentYear: 4, totalStrength: 130 },
    { id: 5, name: "Batch 2019", currentYear: 5, totalStrength: 125 },
  ]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [isReviewOpen, setIsReviewOpen] = useState(false);
  const [csvData, setCsvData] = useState(null);
  const [newBatch, setNewBatch] = useState({
    id: null,
    name: "",
    currentYear: 1,
    totalStrength: 0,
  });
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const yearOptions = [
    { value: "1", label: "1st Year" },
    { value: "2", label: "2nd Year" },
    { value: "3", label: "3rd Year" },
    { value: "4", label: "4th Year" },
    { value: "5", label: "5th Year" },
  ];

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewBatch((prev) => ({ ...prev, [id]: value }));
  };

  const handleYearChange = (value) => {
    setNewBatch((prev) => ({ ...prev, currentYear: parseInt(value) }));
  };

  const handleAddBatch = () => {
    if (newBatch.id) {
      setBatches((prev) =>
        prev.map((batch) => (batch.id === newBatch.id ? newBatch : batch))
      );
    } else {
      setBatches((prev) => [...prev, { ...newBatch, id: prev.length + 1 }]);
    }
    setNewBatch({ id: null, name: "", currentYear: 1, totalStrength: 0 });
    setIsAddModalOpen(false);
    setIsEditModalOpen(false);
  };

  const handleEditBatch = (batch) => {
    setNewBatch(batch);
    setIsEditModalOpen(true);
  };

  const handleDeleteBatch = (id) => {
    setBatches((prev) => prev.filter((batch) => batch.id !== id));
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

  const BatchForm = ({ isEdit = false }) => (
    <div className="grid gap-6 py-4">
      <div className="grid grid-cols-4 items-center gap-4">
        <Label
          htmlFor="name"
          className="text-right text-blue-800 font-semibold"
        >
          Name
        </Label>
        <Input
          id="name"
          value={newBatch.name}
          onChange={handleInputChange}
          className="col-span-3 border-blue-300 focus:border-blue-500 rounded-lg"
        />
      </div>
      <div className="grid grid-cols-4 items-center gap-4">
        <Label
          htmlFor="currentYear"
          className="text-right text-blue-800 font-semibold"
        >
          Current Year
        </Label>
        <Select
          onValueChange={handleYearChange}
          value={newBatch.currentYear.toString()}
        >
          <SelectTrigger className="col-span-3 border-blue-300 focus:border-blue-500 rounded-lg">
            <SelectValue placeholder="Select Year" />
          </SelectTrigger>
          <SelectContent className="bg-blue-50 border-blue-200">
            {yearOptions.map((option) => (
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
      <div className="grid grid-cols-4 items-center gap-4">
        <Label
          htmlFor="totalStrength"
          className="text-right text-blue-800 font-semibold"
        >
          Total Strength
        </Label>
        <Input
          id="totalStrength"
          type="number"
          value={newBatch.totalStrength}
          onChange={handleInputChange}
          className="col-span-3 border-blue-300 focus:border-blue-500 rounded-lg"
        />
      </div>
      <Button
        onClick={handleAddBatch}
        className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2 rounded-lg shadow-md transition duration-300 ease-in-out"
      >
        {isEdit ? "Update Batch" : "Add Batch"}
      </Button>
    </div>
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = batches.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(batches.length / itemsPerPage);

  return (
    <div className="container mx-auto p-4 sm:p-6 bg-blue-50">
      <h1 className="text-2xl font-bold mb-6 text-blue-900">Manage Batches</h1>

      <div className="flex flex-col sm:flex-row justify-end mb-6 space-y-2 sm:space-y-0 sm:space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded-lg shadow-md transition duration-300 ease-in-out w-full sm:w-auto">
              Add
            </Button>
          </DialogTrigger>
          <DialogContent className="bg-blue-50 border-2 border-blue-200 rounded-xl shadow-lg">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold text-blue-900">
                Add New Batch
              </DialogTitle>
            </DialogHeader>
            <BatchForm />
          </DialogContent>
        </Dialog>

        <Dialog open={isBulkUploadOpen} onOpenChange={setIsBulkUploadOpen}>
          <DialogTrigger asChild>
            <Button
              variant="outline"
              className="border-2 border-blue-600 text-blue-600 hover:bg-blue-100 font-semibold px-4 py-2 rounded-lg shadow-md transition duration-300 ease-in-out w-full sm:w-auto"
            >
              Bulk Upload
            </Button>
          </DialogTrigger>
          <DialogContent className="bg-blue-50 border-2 border-blue-200 rounded-xl shadow-lg">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold text-blue-900">
                Upload CSV File
              </DialogTitle>
            </DialogHeader>
            <Input
              type="file"
              accept=".csv"
              onChange={handleFileUpload}
              className="border-blue-300 focus:border-blue-500 rounded-lg"
            />
          </DialogContent>
        </Dialog>
      </div>

      <div className="shadow-md rounded-lg border border-blue-200 overflow-hidden">
        <Table className="min-w-full divide-y">
          <TableHeader className="bg-blue-200 font-bold">
            <TableRow>
              <TableCell className="px-4 py-2 text-blue-900">ID</TableCell>
              <TableCell className="px-4 py-2 text-blue-900">Name</TableCell>
              <TableCell className="px-4 py-2 text-blue-900">
                Current Year of Batch
              </TableCell>
              <TableCell className="px-4 py-2 text-blue-900">
                Total Strength
              </TableCell>
              <TableCell className="px-4 py-2 text-blue-900">Actions</TableCell>
            </TableRow>
          </TableHeader>
          <TableBody>
            {currentItems.map((batch) => (
              <TableRow
                key={batch.id}
                className="transition-colors duration-200 bg-blue-50 hover:bg-blue-100 font-medium"
              >
                <TableCell className="px-4 py-2 text-blue-800">
                  {batch.id}
                </TableCell>
                <TableCell className="px-4 py-2 text-blue-800">
                  {batch.name}
                </TableCell>
                <TableCell className="px-4 py-2 text-blue-800">
                  {batch.currentYear}
                </TableCell>
                <TableCell className="px-4 py-2 text-blue-800">
                  {batch.totalStrength}
                </TableCell>
                <TableCell className="px-4 py-2">
                  <div className="flex space-x-2">
                    <Button
                      onClick={() => handleEditBatch(batch)}
                      className="flex items-center space-x-1 bg-blue-500 hover:bg-blue-600 text-white py-2 px-3 rounded-lg transition-all duration-150 ease-in-out"
                    >
                      <Pencil className="h-4 w-4" />
                      <span>Edit</span>
                    </Button>
                    <Button
                      onClick={() => handleDeleteBatch(batch.id)}
                      className="flex items-center space-x-1 bg-red-500 hover:bg-red-600 text-white py-2 px-3 rounded-lg transition-all duration-150 ease-in-out"
                    >
                      <Trash2 className="h-4 w-4" />
                      <span>Delete</span>
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>

      <div className="flex justify-between items-center mt-4">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
          className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg shadow-md transition duration-300 ease-in-out"
        >
          <ChevronLeft className="h-4 w-4 mr-2" />
          Previous
        </Button>
        <span className="text-blue-800">
          Page {currentPage} of {totalPages}
        </span>
        <Button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages}
          className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg shadow-md transition duration-300 ease-in-out"
        >
          Next
          <ChevronRight className="h-4 w-4 ml-2" />
        </Button>
      </div>

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent className="bg-blue-50 border-2 border-blue-200 rounded-xl shadow-lg">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold text-blue-900">
              Edit Batch
            </DialogTitle>
          </DialogHeader>
          <BatchForm isEdit={true} />
        </DialogContent>
      </Dialog>

      <Dialog open={isReviewOpen} onOpenChange={setIsReviewOpen}>
        <DialogContent className="bg-blue-50 border-2 border-blue-200 rounded-xl shadow-lg">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold text-blue-900">
              Review CSV Data
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <p className="text-blue-800 font-semibold">
              Review the data from the CSV file:
            </p>
            <pre className="bg-blue-100 p-4 rounded-lg mt-2 max-h-60 overflow-auto text-blue-800 font-mono text-sm">
              {csvData}
            </pre>
          </div>
          <Button
            onClick={() => setIsReviewOpen(false)}
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2 rounded-lg shadow-md transition duration-300 ease-in-out"
          >
            Upload Data
          </Button>
        </DialogContent>
      </Dialog>
    </div>
  );
}
