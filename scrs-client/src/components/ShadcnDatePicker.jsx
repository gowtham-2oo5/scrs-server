"use client";
import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Calendar } from "lucide-react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const ShadcnDatePicker = ({ startYear, endYear, selected, onSelect }) => {
  const [error, setError] = useState(null);
  const [currentDate, setCurrentDate] = useState(
    selected || new Date(startYear, 0, 1)
  );

  const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];
  const years = Array.from(
    { length: endYear - startYear + 1 },
    (_, i) => startYear + i
  );

  useEffect(() => {
    if (selected) {
      setCurrentDate(selected);
    }
  }, [selected]);

  const handleDayChange = (day) => {
    const newDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      parseInt(day)
    );

    if (newDate.getDate() !== parseInt(day)) {
      setError("Invalid date selected");
    } else {
      setError(null);
      setCurrentDate(newDate);
      onSelect(newDate);
    }
  };

  const handleMonthChange = (month) => {
    const newDate = new Date(
      currentDate.getFullYear(),
      months.indexOf(month),
      currentDate.getDate()
    );

    if (newDate.getMonth() !== months.indexOf(month)) {
      setError("Invalid date selected");
    } else {
      setError(null);
      setCurrentDate(newDate);
      onSelect(newDate);
    }
  };

  const handleYearChange = (year) => {
    const newDate = new Date(
      parseInt(year),
      currentDate.getMonth(),
      currentDate.getDate()
    );

    if (newDate.getFullYear() !== parseInt(year)) {
      setError("Invalid date selected");
    } else {
      setError(null);
      setCurrentDate(newDate);
      onSelect(newDate);
    }
  };

  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button variant="outline" className="justify-start w-full">
          <Calendar className="w-4 h-4 mr-2" />
          {currentDate
            ? `${
                months[currentDate.getMonth()]
              } ${currentDate.getDate()}, ${currentDate.getFullYear()}`
            : "Pick a date"}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-auto p-4">
        <div className="grid grid-cols-3 gap-4">
          {/* Day Selector */}
          <Select
            onValueChange={handleDayChange}
            value={currentDate.getDate().toString()}
          >
            <SelectTrigger className="w-full">
              <SelectValue placeholder="Day" />
            </SelectTrigger>
            <SelectContent>
              {Array.from({ length: 31 }, (_, i) => (
                <SelectItem key={i + 1} value={(i + 1).toString()}>
                  {i + 1}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          {/* Month Selector */}
          <Select
            onValueChange={handleMonthChange}
            value={months[currentDate.getMonth()]}
          >
            <SelectTrigger className="w-full">
              <SelectValue placeholder="Month" />
            </SelectTrigger>
            <SelectContent>
              {months.map((month, index) => (
                <SelectItem key={index} value={month}>
                  {month}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          {/* Year Selector */}
          <Select
            onValueChange={handleYearChange}
            value={currentDate.getFullYear().toString()}
          >
            <SelectTrigger className="w-full">
              <SelectValue placeholder="Year" />
            </SelectTrigger>
            <SelectContent>
              {years.map((year) => (
                <SelectItem key={year} value={year.toString()}>
                  {year}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        {error && <div className="mt-2 text-red-500">{error}</div>}
      </PopoverContent>
    </Popover>
  );
};

export default ShadcnDatePicker;
