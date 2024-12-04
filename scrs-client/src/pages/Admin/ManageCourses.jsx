"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
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
} from "@/components/ui/dialog";
import {
  Pencil,
  Trash2,
  ChevronLeft,
  ChevronRight,
  Info,
  Link,
} from "lucide-react";
import { getAllCourses, deleteCourse } from "@/api/courses";
import { getDepts } from "@/api/dept";
import AddCourse from "@/components/AddCourse";
import UpdateCourse from "@/components/UpdateCourse";
import FacultyModal from "@/components/FacultyModal";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { getFacultyById } from "@/api/faculty";
export default function ManageCourses() {
  const [courses, setCourses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [isFacultyModalOpen, setIsFacultyModalOpen] = useState(false);
  const [selectedFaculty, setSelectedFaculty] = useState(null);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const itemsPerPage = 5;

  useEffect(() => {
    fetchCourses();
    fetchDepartments();
  }, []);

  const fetchCourses = async () => {
    const response = await getAllCourses();
    if (response.data) {
      setCourses(response.data);
    }
  };

  const fetchDepartments = async () => {
    const response = await getDepts();
    if (response.data) {
      setDepartments(response.data);
    }
  };

  const handleAddCourse = (newCourse) => {
    setCourses([...courses, newCourse]);
    setIsAddModalOpen(false);
  };

  const handleUpdateCourse = (updatedCourse) => {
    setCourses(
      courses.map((course) =>
        course.id === updatedCourse.id ? updatedCourse : course
      )
    );
    setIsEditModalOpen(false);
  };

  const handleDeleteCourse = async (id) => {
    await deleteCourse(id);
    fetchCourses();
  };

  const handleFacultyClick = async (faculty) => {
    const fac = await getFacultyById(faculty);
    console.log(fac.data);
    setSelectedFaculty(fac.data);
    setIsFacultyModalOpen(true);
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = courses.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(courses.length / itemsPerPage);

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <h1 className="mb-6 text-2xl font-bold">Manage Courses</h1>

      <div className="flex justify-end mb-6">
        <Button
          onClick={() => setIsAddModalOpen(true)}
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
        >
          Add Course
        </Button>
      </div>

      <div className="overflow-hidden border rounded-lg shadow-md">
        <Table className="min-w-full divide-y">
          <TableHeader className="font-bold">
            <TableRow>
              <TableCell className="px-4 py-2">ID</TableCell>
              <TableCell className="px-4 py-2">Title</TableCell>
              <TableCell className="px-4 py-2">Course Code</TableCell>
              <TableCell className="px-4 py-2">Description</TableCell>
              <TableCell className="px-4 py-2">Offering Dept</TableCell>
              <TableCell className="px-4 py-2">Incharge</TableCell>
              <TableCell className="px-4 py-2">Credits</TableCell>
              <TableCell className="px-4 py-2">Actions</TableCell>
            </TableRow>
          </TableHeader>
          <TableBody>
            {currentItems.map((course, ind) => {
              const dynamicIndex = (currentPage - 1) * itemsPerPage + ind + 1;
              return (
                <TableRow
                  key={course.id}
                  className="font-medium transition-colors duration-200"
                >
                  <TableCell className="px-4 py-2">{dynamicIndex}</TableCell>
                  <TableCell className="px-4 py-2">
                    {course.courseTitle}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {course.courseCode}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {course.courseDesc}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    {course.offeringDept}
                  </TableCell>
                  <TableCell className="px-4 py-2">
                    <Button
                      variant="link"
                      className="p-0 text-blue-600 hover:text-blue-800"
                      onClick={() =>
                        handleFacultyClick(course.courseInchargeId)
                      }
                    >
                      {/* <Link className="w-4 h-4 mr-1" /> */}
                      {course.courseIncharge}
                    </Button>
                  </TableCell>
                  <TableCell className="px-4 py-2">{course.credits}</TableCell>
                  <TableCell className="px-4 py-2">
                    <div className="flex space-x-2">
                      <TooltipProvider>
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Button
                              onClick={() => {
                                setSelectedCourse(course);
                                setIsEditModalOpen(true);
                              }}
                              className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                              variant="outline"
                            >
                              <Pencil className="w-4 h-4" />

                              {/* <span>Edit</span> */}
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>
                            <p>Edit</p>
                          </TooltipContent>
                        </Tooltip>
                      </TooltipProvider>
                      <TooltipProvider>
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Button
                              onClick={() => handleDeleteCourse(course.id)}
                              variant="destructive"
                              className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                            >
                              <Trash2 className="w-4 h-4" />
                              {/* <span>Delete</span> */}
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>
                            <p>Delete</p>
                          </TooltipContent>
                        </Tooltip>
                      </TooltipProvider>
                      <TooltipProvider>
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Button
                              variant="outline"
                              className="flex items-center px-3 py-2 space-x-1 transition-all duration-150 ease-in-out rounded-lg"
                            >
                              <Info className="w-4 h-4" />
                              {/* <span>Learn More</span> */}
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>
                            <p>Learn More</p>
                          </TooltipContent>
                        </Tooltip>
                      </TooltipProvider>
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

      <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
        <DialogContent className="border-2 shadow-lg rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Add New Course
            </DialogTitle>
          </DialogHeader>
          <AddCourse
            departments={departments}
            onSuccess={handleAddCourse}
            onCancel={() => setIsAddModalOpen(false)}
          />
        </DialogContent>
      </Dialog>

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent className="border-2 shadow-lg bg-blue-50 rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Edit Course
            </DialogTitle>
          </DialogHeader>
          {selectedCourse && (
            <UpdateCourse
              course={selectedCourse}
              departments={departments}
              onSuccess={handleUpdateCourse}
              onCancel={() => setIsEditModalOpen(false)}
            />
          )}
        </DialogContent>
      </Dialog>

      <FacultyModal
        isOpen={isFacultyModalOpen}
        onClose={() => setSelectedFaculty(null)}
        faculty={selectedFaculty}
      />
    </div>
  );
}
