import React, { useState, useEffect } from 'react';
import {
    Avatar,
    AvatarFallback,
    AvatarImage,
} from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";

import {
    BookOpen,
    Calendar,
    ClipboardList,
    GraduationCap,
    HelpCircle,
    LogOut,
    Menu,
    Users,
    Bell,
    User
} from "lucide-react";

import Sidebar from '../Sidebar';

const AdminLayout = ({ children }) => {
    const [activeUserProfile, setActiveUserProfile] = useState("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?size=626&ext=jpg&ga=GA1.1.454232400.1727809163&semt=ais_hybrid");
    const sidebarItems = [
        {
            icon: BookOpen,
            label: "Courses",
            items: {
                "Manage Courses": "courses/manage",
                "Add New Course": "courses/add",
                "Course Categories": "courses/categories"
            },
        },
        {
            icon: Calendar,
            label: "Schedule",
            items: {
                "View Schedules": "schedule/view",
                "Create Schedule": "schedule/create",
                "Manage Time Slots": "schedule/time-slots"
            },
        },
        {
            icon: GraduationCap,
            label: "Students",
            items: {
                "Student List": "students/list",
                "Add Student": "students/add",
                "Student Registrations": "students/registrations"
            },
        },
        {
            icon: Users,
            label: "Faculty",
            items: {
                "Faculty List": "faculty/list",
                "Add Faculty": "faculty/add",
                "Assign Courses": "faculty/assign-courses"
            },
        },
        {
            icon: ClipboardList,
            label: "Registration",
            items: {
                "Course Registration": "registration/course",
                "Registration Periods": "registration/periods",
                "Waitlist Management": "registration/waitlist"
            },
        },
    ];

    const singleItems = [
        { icon: Bell, label: "Notifications" },
        { icon: HelpCircle, label: "Help & Support" },
    ];

    const [sidebarOpen, setSidebarOpen] = useState(false);

    const [userRole, setUserRole] = useState("");


    useEffect(() => {
        const role = sessionStorage.getItem("role");
        setActiveUserProfile(`data:image/jpeg;base64,${sessionStorage.getItem("dp")}`);
        if (role) {
            if (role === "SUPER-ADMIN")
                setUserRole("Owner");
            else if (role === "ADMIN")
                setUserRole("Admin");
        }
    }, []);

    return (
        <div className="flex h-screen bg-background">
            {/* Sidebar for larger screens */}
            <Sidebar className="hidden lg:block lg:w-64 w-[16rem]" sidebarItems={sidebarItems} singleItems={singleItems} />

            {/* Main content */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {/* Header */}
                <header className="flex items-center justify-between px-6 py-4 border-b">
                    <h1 className="text-2xl font-bold">{userRole} Dashboard</h1>
                    <div className="self-end hidden lg:block">
                        <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                                <Avatar className="cursor-pointer w-12 h-12">
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

                {children}
            </div>
        </div>
    );
}

export default AdminLayout;
