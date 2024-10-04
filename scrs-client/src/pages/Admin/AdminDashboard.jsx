import React, { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { ScrollArea } from "@/components/ui/scroll-area"
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"

import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import {
    BookOpen,
    Calendar,
    ClipboardList,
    GraduationCap,
    HelpCircle,
    LogOut,
    Menu,
    Users,
    AlertCircle,
    Bell,
    User
} from "lucide-react"
import {
    Avatar,
    AvatarFallback,
    AvatarImage,
} from "@/components/ui/avatar"
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
} from "recharts"

import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"

import Sidebar from "@/components/Sidebar"

// Sample data for charts and tables
const enrollmentData = [
    { name: "CSE", students: 120 },
    { name: "ECE", students: 98 },
    { name: "EEE", students: 110 },
    { name: "Civil", students: 85 },
    { name: "Mechanical", students: 75 },
]

const recentActivities = [
    { id: 1, action: "New student registered", timestamp: "2 minutes ago" },
    { id: 2, action: "Course schedule updated", timestamp: "10 minutes ago" },
    { id: 3, action: "New faculty member added", timestamp: "1 hour ago" },
    { id: 4, action: "Registration period opened", timestamp: "2 hours ago" },
    { id: 5, action: "System maintenance completed", timestamp: "1 day ago" },
]

const sidebarItems = [
    {
        icon: BookOpen,
        label: "Courses",
        items: ["Manage Courses", "Add New Course", "Course Categories"],
    },
    {
        icon: Calendar,
        label: "Schedule",
        items: ["View Schedule", "Create Schedule", "Manage Time Slots"],
    },
    {
        icon: GraduationCap,
        label: "Students",
        items: ["Student List", "Add Student", "Student Performance"],
    },
    {
        icon: Users,
        label: "Faculty",
        items: ["Faculty List", "Add Faculty", "Assign Courses"],
    },
    {
        icon: ClipboardList,
        label: "Registration",
        items: ["Course Registration", "Registration Periods", "Waitlist Management"],
    },
]

const singleItems = [
    { icon: Bell, label: "Notifications" },
    //{ icon: FileText, label: "Reports" },
    // { icon: User, label: "Profile" },
    //  { icon: Settings, label: "Settings" },
    { icon: HelpCircle, label: "Help & Support" },
    //{ icon: LogOut, label: "Log Out" },
]

const activeUserProfile = "https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?size=626&ext=jpg&ga=GA1.1.454232400.1727809163&semt=ais_hybrid";

export default function AdminDashboard() {
    const [sidebarOpen, setSidebarOpen] = useState(false)

    return (
        <div className="flex h-screen bg-background">
            {/* Sidebar for larger screens */}
            <Sidebar className="hidden lg:block lg:w-64 w-[16rem]" sidebarItems={sidebarItems} singleItems={singleItems} />

            {/* Main content */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {/* Header */}
                <header className="flex items-center justify-between px-6 py-4 border-b">
                    <h1 className="text-2xl font-bold">Admin Dashboard</h1>
                    <div className="self-end hidden lg:block">
                        <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                                <Avatar className="cursor-pointer">
                                    <AvatarImage src={activeUserProfile} alt="@shadcn" />
                                    <AvatarFallback>CN</AvatarFallback>
                                </Avatar>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent align="end">
                                <DropdownMenuItem>
                                    <User className="mr-2 h-4 w-4" />
                                    <span>Manage Profile</span>
                                </DropdownMenuItem>
                                <DropdownMenuItem>
                                    <LogOut className="mr-2 h-4 w-4" />
                                    <span>Logout</span>
                                </DropdownMenuItem>
                            </DropdownMenuContent>
                        </DropdownMenu>
                    </div>
                    <Sheet open={sidebarOpen} onOpenChange={setSidebarOpen}>
                        <SheetTrigger asChild>
                            <Button variant="outline" size="icon" className="lg:hidden">
                                <Menu className="h-6 w-6" />
                            </Button>
                        </SheetTrigger>
                        <SheetContent side="left" className="p-0 w-64">
                            <Sidebar sidebarItems={sidebarItems} singleItems={singleItems} />
                        </SheetContent>
                    </Sheet>
                </header>

                {/* Dashboard content */}
                <ScrollArea className="flex-1">
                    <div className="p-6 space-y-6">
                        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
                            <Card>
                                <CardHeader className="flex flex-row items-center justify-between pb-2 space-y-0">
                                    <CardTitle className="text-sm font-medium">
                                        Total Students</CardTitle>
                                    <GraduationCap className="w-4 h-4 text-muted-foreground" />
                                </CardHeader>
                                <CardContent>
                                    <div className="text-2xl font-bold">1,234</div>
                                    <p className="text-xs text-muted-foreground">+15% from last month</p>
                                </CardContent>
                            </Card>
                            <Card>
                                <CardHeader className="flex flex-row items-center justify-between pb-2 space-y-0">
                                    <CardTitle className="text-sm font-medium">Active Courses</CardTitle>
                                    <BookOpen className="w-4 h-4 text-muted-foreground" />
                                </CardHeader>
                                <CardContent>
                                    <div className="text-2xl font-bold">86</div>
                                    <p className="text-xs text-muted-foreground">+3 new this semester</p>
                                </CardContent>
                            </Card>
                            <Card>
                                <CardHeader className="flex flex-row items-center justify-between pb-2 space-y-0">
                                    <CardTitle className="text-sm font-medium">Faculty Members</CardTitle>
                                    <Users className="w-4 h-4 text-muted-foreground" />
                                </CardHeader>
                                <CardContent>
                                    <div className="text-2xl font-bold">72</div>
                                    <p className="text-xs text-muted-foreground">+2 from last month</p>
                                </CardContent>
                            </Card>
                            <Card>
                                <CardHeader className="flex flex-row items-center justify-between pb-2 space-y-0">
                                    <CardTitle className="text-sm font-medium">Upcoming Events</CardTitle>
                                    <Calendar className="w-4 h-4 text-muted-foreground" />
                                </CardHeader>
                                <CardContent>
                                    <div className="text-2xl font-bold">3</div>
                                    <p className="text-xs text-muted-foreground">Next: Faculty Meeting</p>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="grid gap-6 md:grid-cols-2">
                            <Card>
                                <CardHeader>
                                    <CardTitle>Enrollment by Department</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <ResponsiveContainer width="100%" height={300}>
                                        <BarChart data={enrollmentData}>
                                            <CartesianGrid strokeDasharray="3 3" />
                                            <XAxis dataKey="name" />
                                            <YAxis />
                                            <Tooltip />
                                            <Bar dataKey="students" fill="#8884d8" />
                                        </BarChart>
                                    </ResponsiveContainer>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Recent Activities</CardTitle>
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

                        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
                            <Card>
                                <CardHeader>
                                    <CardTitle>Quick Actions</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-2">
                                    <Button className="w-full">Add New Student</Button>
                                    <Button className="w-full">Add New Faculty</Button>
                                    <Button className="w-full">Create Course</Button>
                                    <Button className="w-full">Manage Schedule</Button>
                                    <Button className="w-full">Manage Notifications</Button>
                                </CardContent>
                            </Card>

                            <Card className="md:col-span-2">
                                <CardHeader>
                                    <CardTitle>System Notifications</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="space-y-2">
                                        <div className="flex items-center p-2 bg-yellow-100 rounded">
                                            <AlertCircle className="w-4 h-4 mr-2 text-yellow-600" />
                                            <span className="text-sm">Registration for Fall 2023 opens in 2 days</span>
                                        </div>
                                        <div className="flex items-center p-2 bg-blue-100 rounded">
                                            <AlertCircle className="w-4 h-4 mr-2 text-blue-600" />
                                            <span className="text-sm">System maintenance scheduled for next weekend</span>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Support</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-2">
                                    <Button className="w-full" variant="outline">View Help Docs</Button>
                                    <Button className="w-full" variant="outline">Contact Support</Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </ScrollArea>
            </div>
        </div>
    )
}