import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import {
  BookOpen,
  Calendar,
  GraduationCap,
  Users,
  AlertCircle,
} from "lucide-react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { getDepts } from "@/api/dept";
import { getAllSpecs } from "@/api/specs";
import { getAllBatches } from "@/api/batches";

const recentActivities = [
  { id: 1, action: "New student registered", timestamp: "2 minutes ago" },
  { id: 2, action: "Course schedule updated", timestamp: "10 minutes ago" },
  { id: 3, action: "New faculty member added", timestamp: "1 hour ago" },
  { id: 4, action: "Registration period opened", timestamp: "2 hours ago" },
  { id: 5, action: "System maintenance completed", timestamp: "1 day ago" },
];

const notifications = [
  {
    id: 1,
    title: "Registration Opening Soon",
    description:
      "Registration for Fall 2023 opens in 2 days. Prepare your course selections.",
    action: "View Details",
  },
  {
    id: 2,
    title: "Scheduled Maintenance",
    description:
      "System maintenance scheduled for next weekend. Plan accordingly.",
    action: "Learn More",
  },
  {
    id: 3,
    title: "New Faculty Member Added",
    description:
      "Dr. Jane Doe has joined the Computer Science department. Welcome her on board!",
    action: "View Profile",
  },
  {
    id: 4,
    title: "Course Schedule Updated",
    description:
      "The schedule for Spring 2024 courses has been updated. Check out the changes.",
    action: "View Updates",
  },
  {
    id: 5,
    title: "Exam Results Released",
    description:
      "Results for the recent semester exams are now available. Check your grades.",
    action: "View Results",
  },
];

export default function AdminDashboard() {
  const [deptsData, setDeptsData] = useState([]);
  const [specsData, setSpecsData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const depts = await getDepts();
        const specs = await getAllBatches();

        setDeptsData(
          depts.data.map((dept) => ({
            name: dept.sn,
            Students: dept.studentCount,
          }))
        );

        setSpecsData(
          specs.data.map((spec) => ({
            name: spec.name,
            Students: spec.studentCount,
          }))
        );
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="p-6 space-y-6">
      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
        <Card className="rounded-lg shadow-lg">
          <CardHeader className="flex flex-row items-center justify-between pb-2">
            <CardTitle className="text-sm font-semibold text-gray-700">
              Total Students
            </CardTitle>
            <GraduationCap className="w-5 h-5 text-blue-500" />
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-blue-700">1,234</div>
            <p className="text-sm text-gray-500">+15% from last month</p>
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg">
          <CardHeader className="flex flex-row items-center justify-between pb-2">
            <CardTitle className="text-sm font-semibold text-gray-700">
              Active Courses
            </CardTitle>
            <BookOpen className="w-5 h-5 text-blue-500" />
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-blue-700">86</div>
            <p className="text-sm text-gray-500">+3 new this semester</p>
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg">
          <CardHeader className="flex flex-row items-center justify-between pb-2">
            <CardTitle className="text-sm font-semibold text-gray-700">
              Faculty Members
            </CardTitle>
            <Users className="w-5 h-5 text-blue-500" />
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-blue-700">72</div>
            <p className="text-sm text-gray-500">+2 from last month</p>
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg">
          <CardHeader className="flex flex-row items-center justify-between pb-2">
            <CardTitle className="text-sm font-semibold text-gray-700">
              Upcoming Events
            </CardTitle>
            <Calendar className="w-5 h-5 text-blue-500" />
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-blue-700">3</div>
            <p className="text-sm text-gray-500">Next: Faculty Meeting</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="rounded-lg shadow-lg">
          <CardHeader>
            <CardTitle className="text-lg font-semibold text-gray-800">
              Enrollment by Department
            </CardTitle>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <div className="flex items-center justify-center h-[300px]">
                <div className="w-12 h-12 border-t-2 border-b-2 border-blue-500 rounded-full animate-spin"></div>
              </div>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={deptsData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="name"
                    tick={{ fill: "#4B5563" }}
                    tickLine={false}
                    axisLine={true}
                    interval="preserveStartEnd"
                  />

                  <YAxis
                    tick={{ fill: "#4B5563" }}
                    tickLine={true}
                    axisLine={true}
                  />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "#F3F4F6",
                      border: "none",
                    }}
                  />
                  <Bar
                    dataKey="Students"
                    fill="#1F509A"
                    radius={[4, 4, 0, 0]}
                  />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg">
          <CardHeader>
            <CardTitle className="text-lg font-semibold text-gray-800">
              Enrollment by Batches
            </CardTitle>
          </CardHeader>
          <CardContent>
            {isLoading ? (
              <div className="flex items-center justify-center h-[300px]">
                <div className="w-12 h-12 border-t-2 border-b-2 border-blue-500 rounded-full animate-spin"></div>
              </div>
            ) : (
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={specsData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="name"
                    tick={{ fill: "#4B5563" }}
                    tickLine={true}
                    axisLine={true}
                    interval="preserveStartEnd"
                  />
                  <YAxis
                    tick={{ fill: "#4B5563" }}
                    tickLine={true}
                    axisLine={true}
                  />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "#F3F4F6",
                      border: "none",
                    }}
                  />
                  <Bar
                    dataKey="Students"
                    fill="#0A97B0"
                    radius={[4, 4, 0, 0]}
                  />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
        <Card className="rounded-lg shadow-lg">
          <CardHeader>
            <CardTitle className="text-lg font-semibold text-gray-800">
              Quick Actions
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-3">
            <Button className="w-full text-white bg-blue-600 hover:bg-blue-700">
              Add New Student
            </Button>
            <Button className="w-full text-white bg-blue-600 hover:bg-blue-700">
              Add New Faculty
            </Button>
            <Button className="w-full text-white bg-blue-600 hover:bg-blue-700">
              Create Course
            </Button>
            <Button className="w-full text-white bg-blue-600 hover:bg-blue-700">
              Manage Schedule
            </Button>
            <Button className="w-full text-white bg-blue-600 hover:bg-blue-700">
              Manage Notifications
            </Button>
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg md:col-span-2">
          <CardHeader>
            <CardTitle className="text-lg font-semibold text-gray-800">
              System Notifications
            </CardTitle>
          </CardHeader>
          <CardContent>
            <ScrollArea className="h-[400px]">
              <div className="space-y-4">
                {notifications.map((notification) => (
                  <div
                    key={notification.id}
                    className="flex items-start p-4 space-x-4 border border-blue-200 rounded-lg bg-blue-50"
                  >
                    <AlertCircle className="w-6 h-6 text-blue-600" />
                    <div className="flex-1">
                      <h4 className="text-sm font-semibold text-blue-800">
                        {notification.title}
                      </h4>
                      <p className="mt-1 text-sm text-blue-600">
                        {notification.description}
                      </p>
                      <Button
                        variant="outline"
                        size="sm"
                        className="mt-2 text-blue-600 border-blue-300 hover:bg-blue-100"
                      >
                        {notification.action}
                      </Button>
                    </div>
                  </div>
                ))}
              </div>
            </ScrollArea>
          </CardContent>
        </Card>

        <Card className="rounded-lg shadow-lg">
          <CardHeader>
            <CardTitle className="text-lg font-semibold text-gray-800">
              Recent Activities
            </CardTitle>
          </CardHeader>
          <CardContent>
            <ScrollArea className="h-[300px]">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Action</TableHead>
                    <TableHead>Time</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {recentActivities.map((activity) => (
                    <TableRow key={activity.id}>
                      <TableCell>{activity.action}</TableCell>
                      <TableCell>{activity.timestamp}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </ScrollArea>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
