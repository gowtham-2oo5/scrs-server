import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ChevronLeft, ChevronRight } from "lucide-react";
import AddFacultyForm from "@/components/AddFacultyForm";
import BulkUpload from "@/components/BulkUploadFaculty";
import FacultyTable from "@/components/FacultyTable";
import FacultyModal from "@/components/FacultyModal";
import { getAllFaculties } from "@/api";

export default function FacultyManagement() {
  const [faculty, setFaculty] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [editingFaculty, setEditingFaculty] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [selectedFaculty, setSelectedFaculty] = useState(null);
  const itemsPerPage = 5;

  const fetchFaculty = async (page) => {
    try {
      console.log("Fetching faculty for page:", page);
      const response = await getAllFaculties(page - 1, itemsPerPage);
      console.log("API Response:", response.data.content);
      setFaculty(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Error fetching faculty:", error);
    }
  };

  useEffect(() => {
    fetchFaculty(currentPage);
  }, [currentPage]);

  const handleAddFaculty = (newFaculty) => {
    if (newFaculty.id) {
      setFaculty((prev) =>
        prev.map((fac) => (fac.id === newFaculty.id ? newFaculty : fac))
      );
    } else {
      setFaculty((prev) => [...prev, { ...newFaculty, id: prev.length + 1 }]);
    }
    setIsAddModalOpen(false);
    setIsEditModalOpen(false);
    fetchFaculty(currentPage); // Refresh the faculty list after adding
  };

  const handleEditFaculty = (fac) => {
    setEditingFaculty(fac);
    setIsEditModalOpen(true);
  };

  const handleDeleteFaculty = async (id) => {
    // Implement delete API call here
    // After successful deletion:
    await fetchFaculty(currentPage);
  };

  const handleLearnMore = (fac) => {
    setSelectedFaculty(fac);
  };

  const handleBulkUpload = async (csvData) => {
    // Implement bulk upload API call here
    // After successful upload:
    setIsBulkUploadOpen(false);
    await fetchFaculty(1); // Refresh and go to the first page
    setCurrentPage(1);
  };

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <h1 className="mb-6 text-2xl font-bold">Manage Faculty</h1>

      <div className="flex flex-col justify-end mb-6 space-y-2 sm:flex-row sm:space-y-0 sm:space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-700">
              Add Faculty
            </Button>
          </DialogTrigger>
          <DialogContent className="border-2 shadow-lg rounded-xl">
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold">
                Add New Faculty
              </DialogTitle>
            </DialogHeader>
            <AddFacultyForm onAddFaculty={handleAddFaculty} />
          </DialogContent>
        </Dialog>

        <Dialog open={isBulkUploadOpen} onOpenChange={setIsBulkUploadOpen}>
          <DialogTrigger asChild>
            <Button
              variant="outline"
              className="px-4 py-2 font-semibold text-gray-600 transition duration-300 ease-in-out border-2 rounded-lg shadow-md hover:bg-gray-100"
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
            <BulkUpload onUpload={handleBulkUpload} />
          </DialogContent>
        </Dialog>
      </div>

      <FacultyTable
        faculty={faculty}
        onEdit={handleEditFaculty}
        onDelete={handleDeleteFaculty}
        onLearnMore={handleLearnMore}
        currentPage={currentPage}
        itemsPerPage={itemsPerPage}
      />

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

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent className="border-2 shadow-lg rounded-xl">
          <DialogHeader>
            <DialogTitle className="text-2xl font-bold">
              Edit Faculty
            </DialogTitle>
          </DialogHeader>
          <AddFacultyForm
            onAddFaculty={handleAddFaculty}
            isEdit={true}
            initialFaculty={editingFaculty}
          />
        </DialogContent>
      </Dialog>

      <FacultyModal
        faculty={selectedFaculty}
        onClose={() => setSelectedFaculty(null)}
      />
    </div>
  );
}
