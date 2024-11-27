import React from "react";
import {
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
} from "@/components/ui/table";

const CsvTable = ({ csvData }) => {
  const rows = csvData.split("\n").map((row) => row.split(","));

  return (
    <div className="mt-4 overflow-scroll">
      <p className=" font-semibold">Review the data from the CSV file:</p>
      <div className=" p-4 rounded-lg mt-2 max-h-60 overflow-auto">
        <Table className="w-full font-mono text-sm">
          <TableHeader>
            <TableRow>
              {rows[0].map((header, index) => (
                <TableHead key={index} className="px-2 py-2  font-semibold">
                  {header}
                </TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody>
            {rows.slice(1).map((row, rowIndex) => (
              <TableRow key={rowIndex}>
                {row.map((cell, cellIndex) => (
                  <TableCell
                    key={cellIndex}
                    className="px-2 py-2 text-gray-800"
                  >
                    {cell}
                  </TableCell>
                ))}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  );
};

export default CsvTable;
