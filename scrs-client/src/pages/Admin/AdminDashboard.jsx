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
import {
    Accordion,
    AccordionContent,
    AccordionItem,
    AccordionTrigger,
} from "@/components/ui/accordion"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import {
    BookOpen,
    Calendar,
    ChevronRight,
    ClipboardList,
    FileText,
    GraduationCap,
    HelpCircle,
    LogOut,
    Menu,
    Settings,
    Users,
    AlertCircle,
    Bell,
    User
} from "lucide-react"

import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
} from "recharts"

// Sample data for charts and tables
const enrollmentData = [
    { name: "Computer Science", students: 120 },
    { name: "Engineering", students: 98 },
    { name: "Business", students: 110 },
    { name: "Arts", students: 85 },
    { name: "Medicine", students: 75 },
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
    { icon: FileText, label: "Reports" },
    { icon: User, label: "Profile" },
    { icon: Settings, label: "Settings" },
    { icon: HelpCircle, label: "Help & Support" },
    { icon: LogOut, label: "Log Out" },
]

const Sidebar = ({ className }) => (
    <Card className={`h-full rounded-none border-r ${className}`}>
        <CardHeader>
            <CardTitle>Admin Dashboard</CardTitle>
        </CardHeader>
        <ScrollArea className="h-[calc(100vh-5rem)]">
            <CardContent>
                <Accordion type="multiple" className="w-full">
                    {sidebarItems.map((item, index) => (
                        <AccordionItem value={`item-${index}`} key={index}>
                            <AccordionTrigger>
                                <div className="flex items-center gap-2">
                                    {React.createElement(item.icon, { className: "h-4 w-4" })}
                                    {item.label}
                                </div>
                            </AccordionTrigger>
                            <AccordionContent>
                                <div className="flex flex-col space-y-2 pl-6">
                                    {item.items.map((subItem, subIndex) => (
                                        <Button
                                            key={subIndex}
                                            variant="ghost"
                                            className="justify-start gap-2"
                                        >
                                            <ChevronRight className="h-4 w-4" />
                                            {subItem}
                                        </Button>
                                    ))}
                                </div>
                            </AccordionContent>
                        </AccordionItem>
                    ))}
                </Accordion>
                <div className="mt-4 space-y-2">
                    {singleItems.map((item, index) => (
                        <Button
                            key={index}
                            variant="ghost"
                            className="w-full justify-start gap-2"
                        >
                            {React.createElement(item.icon, { className: "h-4 w-4" })}
                            {item.label}
                        </Button>
                    ))}
                </div>
            </CardContent>
        </ScrollArea>
    </Card>
)

export default function AdminDashboard() {
    const [sidebarOpen, setSidebarOpen] = useState(false)

    return (
        <div className="flex h-screen bg-background">
            {/* Sidebar for larger screens */}
            <Sidebar className="hidden lg:block w-64" />

            {/* Main content */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {/* Header */}
                <header className="flex items-center justify-between px-6 py-4 border-b">
                    <h1 className="text-2xl font-bold">Admin Dashboard</h1>
                    <Sheet open={sidebarOpen} onOpenChange={setSidebarOpen}>
                        <SheetTrigger asChild>
                            <Button variant="outline" size="icon" className="lg:hidden">
                                <Menu className="h-6 w-6" />
                            </Button>
                        </SheetTrigger>
                        <SheetContent side="left" className="p-0 w-64">
                            <Sidebar />
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
                                    <Button className="w-full">Create Course</Button>
                                    <Button className="w-full">Manage Schedule</Button>
                                    <Button className="w-full">Generate Reports</Button>
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