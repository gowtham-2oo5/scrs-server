"use client";

import { useState, useEffect, useCallback } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Plus, Upload, ChevronLeft, ChevronRight } from "lucide-react";
import DepartmentTable from "@/components/DepartmentsTable";
import AddDepartmentForm from "@/components/AddDepartmentForm";
import BulkUpload from "@/components/BulkUploadDepts";
import { getDepts, deleteDept } from "@/api/dept";
import { useToast } from "@/hooks/use-toast";
import { Loader2 } from "lucide-react";

export default function DepartmentManagement() {
  const [departments, setDepartments] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isBulkUploadOpen, setIsBulkUploadOpen] = useState(false);
  const [editingDepartment, setEditingDepartment] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();
  const itemsPerPage = 7;

  const fetchDepts = useCallback(async () => {
    setIsLoading(true);
    try {
      const response = await getDepts();
      setDepartments(response.data);
      toast({
        title: "Departments fetched successfully",
        description: `${response.data.length} departments loaded.`,
      });
    } catch (error) {
      console.error("Error fetching departments:", error);
      toast({
        title: "Error fetching departments",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  }, [toast]);

  useEffect(() => {
    fetchDepts();
  }, [fetchDepts]);

  const handleAddDepartment = async (newDepartment) => {
    setIsLoading(true);
    try {
      if (newDepartment.id) {
        setDepartments((prev) =>
          prev.map((dept) =>
            dept.id === newDepartment.id ? newDepartment : dept
          )
        );
        toast({
          title: "Department updated",
          description: `${newDepartment.name} has been updated successfully.`,
        });
      } else {
        setDepartments((prev) => [
          ...prev,
          { ...newDepartment, id: prev.length + 1 },
        ]);
        toast({
          title: "Department added",
          description: `${newDepartment.name} has been added successfully.`,
        });
      }
      setIsAddModalOpen(false);
      setIsEditModalOpen(false);
      await fetchDepts();
    } catch (error) {
      toast({
        title: "Error",
        description: `Failed to ${
          newDepartment.id ? "update" : "add"
        } department: ${error.message}`,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleEditDepartment = (dept) => {
    setEditingDepartment(dept);
    setIsEditModalOpen(true);
  };

  const handleDeleteDepartment = async (id) => {
    setIsLoading(true);
    try {
      await deleteDept(id);
      setDepartments((prev) => prev.filter((dept) => dept.id !== id));
      toast({
        title: "Department deleted",
        description: "The department has been deleted successfully.",
      });
      await fetchDepts();
    } catch (error) {
      toast({
        title: "Error deleting department",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleBulkUpload = async (csvData) => {
    setIsLoading(true);
    try {
      const newDepartments = csvData
        .split("\n")
        .slice(1)
        .map((row, index) => {
          const [name, sn, hod] = row.split(",");
          return { id: departments.length + index + 1, name, sn, hod };
        });
      setDepartments((prev) => [...prev, ...newDepartments]);
      setIsBulkUploadOpen(false);
      toast({
        title: "Bulk upload successful",
        description: `${newDepartments.length} departments have been added.`,
      });
      await fetchDepts();
    } catch (error) {
      toast({
        title: "Error during bulk upload",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const totalPages = Math.ceil(departments.length / itemsPerPage);
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = departments.slice(indexOfFirstItem, indexOfLastItem);

  return (
    <div className="container p-4 mx-auto sm:p-6">
      <h1 className="mb-6 text-2xl font-bold text-black">Manage Departments</h1>

      <div className="flex justify-end mb-4 space-x-4">
        <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
          <DialogTrigger asChild>
            <Button className="px-4 py-2 font-semibold text-white bg-gray-900 rounded-md hover:bg-gray-700">
              <Plus className="w-4 h-4 mr-2" />
              Add
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New Department</DialogTitle>
            </DialogHeader>
            <AddDepartmentForm onAddDepartment={handleAddDepartment} />
          </DialogContent>
        </Dialog>

        <Dialog open={isBulkUploadOpen} onOpenChange={setIsBulkUploadOpen}>
          <DialogTrigger asChild>
            <Button className="px-4 py-2 font-semibold text-black bg-gray-200 rounded-md hover:bg-gray-300">
              <Upload className="w-4 h-4 mr-2" />
              Bulk Upload
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Upload CSV</DialogTitle>
            </DialogHeader>
            <BulkUpload onUpload={handleBulkUpload} />
          </DialogContent>
        </Dialog>
      </div>

      {isLoading ? (
        <div className="flex items-center justify-center h-64">
          <Loader2 className="w-8 h-8 animate-spin" />
        </div>
      ) : (
        <DepartmentTable
          departments={currentItems}
          onEdit={handleEditDepartment}
          onDelete={handleDeleteDepartment}
          currentPage={currentPage}
          itemsPerPage={itemsPerPage}
        />
      )}

      <div className="flex items-center justify-between px-4 py-2 mt-4">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1 || isLoading}
          className="px-4 py-2 font-semibold text-white bg-gray-900 rounded-md hover:bg-gray-900 disabled:bg-gray-400"
        >
          <ChevronLeft className="w-4 h-4 mr-1" />
          Previous
        </Button>
        <div className="text-gray-700">
          Page {currentPage} of {totalPages}
        </div>
        <Button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages || isLoading}
          className="px-4 py-2 font-semibold text-white bg-gray-900 rounded-md hover:bg-gray-900 disabled:bg-gray-400"
        >
          Next
          <ChevronRight className="w-4 h-4 ml-1" />
        </Button>
      </div>

      <Dialog open={isEditModalOpen} onOpenChange={setIsEditModalOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Edit Department</DialogTitle>
          </DialogHeader>
          <AddDepartmentForm
            onAddDepartment={handleAddDepartment}
            isEdit
            initialDepartment={editingDepartment}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
