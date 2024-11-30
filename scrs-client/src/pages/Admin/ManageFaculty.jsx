import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import AddFacultyForm from "@/components/AddFacultyForm";
import BulkUpload from "@/components/BulkUploadFaculty";
import FacultyTable from "@/components/FacultyTable";
import FacultyModal from "@/components/FacultyModal";
import { getAllFaculties, createNewFaculty } from "@/api/faculty";
import toast, { Toaster } from "react-hot-toast";

export default function FacultyManagement() {
  const [faculty, setFaculty] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [editingFaculty, setEditingFaculty] = useState(null);
  const [selectedFaculty, setSelectedFaculty] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchFaculty = async () => {
    try {
      const response = await getAllFaculties();
      // console.log("API Response:", response.data);
      setFaculty(response.data);
    } catch (error) {
      console.error("Error fetching faculty:", error);
      toast.error("Failed to fetch faculty data");
    }
  };

  useEffect(() => {
    // console.log("Fetching faculty");
    fetchFaculty();
  }, []);

  const handleAddFaculty = async (newFaculty) => {
    setIsLoading(true);
    try {
      const res = await createNewFaculty(newFaculty);
      if (res.status === 200) {
        toast.success("Faculty added successfully");
        setIsAddModalOpen(false);
        setIsEditModalOpen(false);
        await fetchFaculty();
      } else {
        toast.error(res.data.message || "Failed to add faculty");
      }
    } catch (error) {
      console.error("Error adding/editing faculty:", error);
      toast.error("An unexpected error occurred");
    } finally {
      setIsLoading(false);
    }
  };

  const handleEditFaculty = (facultyToEdit) => {
    setEditingFaculty(facultyToEdit);
    setIsEditModalOpen(true);
  };

  const handleDeleteFaculty = async (id) => {
    try {
      // Call the delete API here
      // console.log(`Deleting faculty with ID: ${id}`);
      // After deletion, refresh the faculty list
      await fetchFaculty();
      toast.success("Faculty deleted successfully");
    } catch (error) {
      console.error("Error deleting faculty:", error);
      toast.error("Failed to delete faculty");
    }
  };

  const handleLearnMore = (facultyDetails) => {
    setSelectedFaculty(facultyDetails);
  };

  const handleBulkUpload = async (csvData) => {
    try {
      // Implement bulk upload API call here
      // console.log("Uploading CSV data:", csvData);
      setIsBulkUploadOpen(false);
      await fetchFaculty();
      toast.success("Bulk upload completed successfully");
    } catch (error) {
      console.error("Error during bulk upload:", error);
      toast.error("Failed to complete bulk upload");
    }
  };

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <Toaster position="top-right" />
      <h1 className="mb-6 text-2xl font-bold">Manage Faculty</h1>

      {/* Add and Bulk Upload Buttons */}
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
            <AddFacultyForm
              onAddFaculty={handleAddFaculty}
              isLoading={isLoading}
            />
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

      {/* Faculty Table */}
      <FacultyTable
        faculty={faculty}
        onEdit={handleEditFaculty}
        onDelete={handleDeleteFaculty}
        onLearnMore={handleLearnMore}
      />

      {/* Edit Faculty Dialog */}
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
            isLoading={isLoading}
          />
        </DialogContent>
      </Dialog>

      {/* Faculty Modal for Details */}
      <FacultyModal
        faculty={selectedFaculty}
        onClose={() => setSelectedFaculty(null)}
      />
    </div>
  );
}
