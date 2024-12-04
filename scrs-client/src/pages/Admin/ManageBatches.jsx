"use client";

import { useEffect, useState } from "react";
import { useForm, Controller } from "react-hook-form";
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
import { Pencil, Trash2, ChevronLeft, ChevronRight, Pen } from "lucide-react";
import {
  getAllBatches,
  addSingleBatch,
  bulkUploadBatches,
  deleteAllBatches,
  deleteBatchById,
  updateBatchSem,
} from "@/api/batches";

export default function BatchManagement() {
  const [batches, setBatches] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [isReviewOpen, setIsReviewOpen] = useState(false);
  const [csvData, setCsvData] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const { control, handleSubmit, reset } = useForm({
    defaultValues: {
      name: "",
      currentYear: "FIRST",
      currentSem: "FALL",
      eligibleForNextRegs: false,
      studentsCount: 0,
    },
  });

  useEffect(() => {
    fetchBatches();
  }, []);

  const fetchBatches = async () => {
    const response = await getAllBatches();
    if (response.data) {
      setBatches(response.data);
    }
  };

  const yearOptions = [
    { value: "FIRST", label: "1st Year" },
    { value: "SECOND", label: "2nd Year" },
    { value: "THIRD", label: "3rd Year" },
    { value: "FOURTH", label: "4th Year" },
  ];

  const semesterOptions = [
    { value: "ODD", label: "Odd" },
    { value: "EVEN", label: "Even" },
    { value: "SUMMER", label: "Summer" },
  ];

  const onSubmit = async (data) => {
    await addSingleBatch(data);
    fetchBatches();
    reset();
    setIsAddModalOpen(false);
  };

  const handleDeleteBatch = async (id) => {
    await deleteBatchById(id);
    fetchBatches();
  };

  const handleUpdateBatch = async (name) => {
    await updateBatchSem(name);
    fetchBatches();
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

  const handleBulkUpload = async () => {
    const formData = new FormData();
    formData.append(
      "file",
      new Blob([csvData], { type: "text/csv" }),
      "batches.csv"
    );
    await bulkUploadBatches(formData);
    setIsReviewOpen(false);
    fetchBatches();
  };

  const BatchForm = ({ isEdit = false }) => (
    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-6 py-4">
      <div className="grid items-center grid-cols-4 gap-4">
        <Label
          htmlFor="name"
          className="font-semibold text-right text-blue-800"
        >
          Name
        </Label>
        <Controller
          name="name"
          control={control}
          rules={{ required: "Name is required" }}
          render={({ field }) => (
            <Input
              {...field}
              className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500"
            />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label
          htmlFor="currentYear"
          className="font-semibold text-right text-blue-800"
        >
          Year
        </Label>
        <Controller
          name="currentYear"
          control={control}
          render={({ field }) => (
            <Select onValueChange={field.onChange} value={field.value}>
              <SelectTrigger className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500">
                <SelectValue placeholder="Select Year" />
              </SelectTrigger>
              <SelectContent className="border-blue-200 bg-blue-50">
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
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label
          htmlFor="currentSem"
          className="font-semibold text-right text-blue-800"
        >
          Semester
        </Label>
        <Controller
          name="currentSem"
          control={control}
          render={({ field }) => (
            <Select onValueChange={field.onChange} value={field.value}>
              <SelectTrigger className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500">
                <SelectValue placeholder="Select Semester" />
              </SelectTrigger>
              <SelectContent className="border-blue-200 bg-blue-50">
                {semesterOptions.map((option) => (
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
          )}
        />
      </div>
      <Button
        type="submit"
        className="px-6 py-2 font-semibold text-white transition duration-300 ease-in-out bg-blue-600 rounded-lg shadow-md hover:bg-blue-700"
      >
        {isEdit ? "Update Batch" : "Add Batch"}
      </Button>
    </form>
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = batches.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(batches.length / itemsPerPage);

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <h1 className="mb-6 text-2xl font-bold">Manage Batches</h1>

      <div className="flex flex-col justify-end mb-6 space-y-2 sm:flex-row sm:space-y-0 sm:space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="w-full px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-700 sm:w-auto">
              Add
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold">
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
              className="w-full px-4 py-2 font-semibold text-gray-600 transition duration-300 ease-in-out border-2 rounded-lg shadow-md hover:bg-gray-100 sm:w-auto"
            >
              Bulk Upload
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold">
                Upload CSV File
              </DialogTitle>
            </DialogHeader>
            <Input
              type="file"
              accept=".csv"
              onChange={handleFileUpload}
              className="rounded-lg"
            />
          </DialogContent>
        </Dialog>
      </div>

      <div className="overflow-hidden border rounded-lg shadow-md">
        <Table className="min-w-full divide-y">
          <TableHeader className="font-bold">
            <TableRow>
              <TableCell className="px-4 py-2">Id</TableCell>
              <TableCell className="px-4 py-2">Name</TableCell>
              <TableCell className="px-4 py-2">Current Year</TableCell>
              <TableCell className="px-4 py-2">Current Semester</TableCell>
              <TableCell className="px-4 py-2">
                Eligible for Next Registrations
              </TableCell>
              <TableCell className="px-4 py-2">Students Count</TableCell>
              <TableCell className="px-4 py-2">Actions</TableCell>
            </TableRow>
          </TableHeader>
          <TableBody>
            {currentItems.map((batch, index) => {
              const dynamicIndex = (currentPage - 1) * itemsPerPage + index + 1;

              return (
                <TableRow
                  key={batch.name}
                  className="font-medium transition-colors duration-200 hover:bg-gray-100"
                >
                  <TableCell className="px-4 py-2">{dynamicIndex}</TableCell>
                  <TableCell className="px-4 py-2">{batch.name}</TableCell>
                  <TableCell className="px-4 py-2">
                    {batch.currentYear}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {batch.currentSem}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {batch.eligibleForNextRegs ? "Yes" : "No"}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {batch.studentsCount == null ? 0 : batch.studentsCount}
                  </TableCell>
                  <TableCell className="flex items-start gap-3 px-4 py-2 align-middle">
                    <Button
                      onClick={() => handleUpdateBatch(batch.name)}
                      className="flex items-center px-3 py-2 space-x-1 text-white transition-all duration-150 ease-in-out rounded-lg "
                    >
                      <Pen className="w-4 h-4" />
                      <span>Edit</span>
                    </Button>
                    <Button
                      onClick={() => handleDeleteBatch(batch.id)}
                      className="flex items-center px-3 py-2 space-x-1 text-white transition-all duration-150 ease-in-out bg-red-500 rounded-lg hover:bg-red-600"
                    >
                      <Trash2 className="w-4 h-4" />
                      <span>Delete</span>
                    </Button>
                  </TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </div>

      <div className="flex items-center justify-between mt-4">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
          className="px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-600"
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
          className="px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-600"
        >
          Next
          <ChevronRight className="w-4 h-4 ml-2" />
        </Button>
      </div>

      <Dialog open={isReviewOpen} onOpenChange={setIsReviewOpen}>
        <DialogContent className="border-2 shadow-lg rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Review CSV Data
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <p className="font-semibold">Review the data from the CSV file:</p>
            <pre className="p-4 mt-2 overflow-auto font-mono text-sm rounded-lg max-h-60">
              {csvData}
            </pre>
          </div>
          <Button
            onClick={handleBulkUpload}
            className="px-6 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-700"
          >
            Upload Data
          </Button>
        </DialogContent>
      </Dialog>
    </div>
  );
}
