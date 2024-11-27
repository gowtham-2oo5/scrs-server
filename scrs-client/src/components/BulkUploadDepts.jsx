import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import CsvTable from "@/components/CsvTable";
import { bulkUploadDepts } from "@/api";

export default function BulkUpload({ onUpload }) {
  const [csvData, setCsvData] = useState(null);
  const [isReviewOpen, setIsReviewOpen] = useState(false);
  const [csvFile, setCsvFile] = useState(null);

  const handleFileUpload = (event) => {
    const file = event.target.files?.[0];
    setCsvFile(file);
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const content = e.target?.result;
        setCsvData(content);
        setIsReviewOpen(true);
      };
      reader.readAsText(file);
    }
  };

  const handleUpload = async () => {
    if (csvData) {
      onUpload(csvData);
      console.log("Sending dataaa");
      const formData = new FormData();
      formData.append("csv_file", csvFile);
      console.log(csvFile.name);
      const res = await bulkUploadDepts(formData);
      console.log(res.status);
      setIsReviewOpen(false);
    }
  };

  return (
    <div>
      <Input
        type="file"
        accept=".csv"
        onChange={handleFileUpload}
        className="rounded-lg focus:border-blue-500"
      />
      {isReviewOpen && csvData && (
        <div className="mt-4">
          <CsvTable csvData={csvData} />
          <Button
            onClick={handleUpload}
            className="px-6 py-2 mt-4 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-700"
          >
            Upload Data
          </Button>
        </div>
      )}
    </div>
  );
}
