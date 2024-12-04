"use client";

import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
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
import { Label } from "@/components/ui/label";
import { Pencil, Trash2, ChevronLeft, ChevronRight, Link } from "lucide-react";
import {
  getAllCourseCategories,
  insertCourseCategory,
  bulkUploadCourseCategories,
  deleteCourseCategory,
} from "@/api/course_categories";

export default function ManageCourseCategories() {
  const [categories, setCategories] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [isCoursesModalOpen, setIsCoursesModalOpen] = useState(false);
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const { register, handleSubmit, reset, setValue } = useForm();

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const response = await getAllCourseCategories();
    if (response.data) {
      console.log(response.data);
      setCategories(response.data);
    }
  };

  const onSubmit = async (data) => {
    if (isEditModalOpen) {
      // Implement update logic here
    } else {
      await insertCourseCategory(data);
    }
    fetchCategories();
    reset();
    setIsAddModalOpen(false);
    setIsEditModalOpen(false);
  };

  const handleEditCategory = (category) => {
    setValue("title", category.title);
    setValue("descr", category.descr);
    setIsEditModalOpen(true);
  };

  const handleDeleteCategory = async (title) => {
    await deleteCourseCategory(title);
    fetchCategories();
  };

  const handleFileUpload = async (event) => {
    const file = event.target.files[0];
    if (file) {
      await bulkUploadCourseCategories(file);
      fetchCategories();
      setIsBulkUploadOpen(false);
    }
  };

  const handleViewCourses = (courses) => {
    setSelectedCourses(courses.map((course) => course.courseTitle));
    setIsCoursesModalOpen(true);
  };

  const CategoryForm = ({ isEdit = false }) => (
    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-6 py-4">
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="title" className="font-semibold text-right">
          Name
        </Label>
        <Input
          id="title"
          {...register("title", { required: "Title is required" })}
          className="col-span-3 rounded-lg"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="descr" className="font-semibold text-right">
          Description
        </Label>
        <Input
          id="descr"
          {...register("descr", { required: "Description is required" })}
          className="col-span-3 rounded-lg"
        />
      </div>
      <Button
        type="submit"
        className="px-6 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
      >
        {isEdit ? "Update Category" : "Add Category"}
      </Button>
    </form>
  );

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = categories.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(categories.length / itemsPerPage);

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <h1 className="mb-6 text-2xl font-bold">Manage Course Categories</h1>

      <div className="flex flex-col justify-end mb-6 space-y-2 sm:flex-row sm:space-y-0 sm:space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="w-full px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md sm:w-auto">
              Add
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold">
                Add New Course Category
              </DialogTitle>
            </DialogHeader>
            <CategoryForm />
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
              <TableCell className="px-4 py-2">ID</TableCell>
              <TableCell className="px-4 py-2">Name</TableCell>
              <TableCell className="px-4 py-2">Description</TableCell>
              <TableCell className="px-4 py-2">Sessions Per Week</TableCell>
              <TableCell className="px-4 py-2">Courses</TableCell>
              <TableCell className="px-4 py-2">Actions</TableCell>
            </TableRow>
          </TableHeader>
          <TableBody>
            {currentItems.map((category, ind) => {
              const dynamicIndex = (currentPage - 1) * itemsPerPage + ind + 1;
              return (
                <TableRow
                  key={category.id}
                  className="font-medium transition-colors duration-200"
                >
                  <TableCell className="px-4 py-2">{dynamicIndex}</TableCell>
                  <TableCell className="px-4 py-2">{category.title}</TableCell>
                  <TableCell className="px-4 py-2">
                    {category.description}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {category.minSessionsPerWeek} -{" "}
                    {category.maxSessionsPerWeek}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    <Button
                      onClick={() => handleViewCourses(category.courses)}
                      className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                      variant="outline"
                    >
                      <Link className="w-4 h-4" />
                      <span>View Courses</span>
                    </Button>
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    <div className="flex space-x-2">
                      <Button
                        onClick={() => handleEditCategory(category)}
                        className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                        variant="outline"
                      >
                        <Pencil className="w-4 h-4" />
                        <span>Edit</span>
                      </Button>
                      <Button
                        onClick={() => handleDeleteCategory(category.title)}
                        variant="destructive"
                        className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                      >
                        <Trash2 className="w-4 h-4" />
                        <span>Delete</span>
                      </Button>
                    </div>
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
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
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
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
        >
          Next
          <ChevronRight className="w-4 h-4 ml-2" />
        </Button>
      </div>

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent className="border-2 shadow-lg bg-blue-50 rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Edit Course Category
            </DialogTitle>
          </DialogHeader>
          <CategoryForm isEdit={true} />
        </DialogContent>
      </Dialog>

      <Dialog open={isCoursesModalOpen} onOpenChange={setIsCoursesModalOpen}>
        <DialogContent className="border-2 shadow-lg rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Courses in Category
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4 overflow-scroll max-h-96">
            <ul className="space-y-2">
              {selectedCourses.map((course, index) => (
                <li key={index} className="p-2 bg-gray-100 rounded-lg">
                  {course}
                </li>
              ))}
            </ul>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
