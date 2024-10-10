import React from 'react'
import { Link } from 'react-router-dom'
import { Button } from "@/components/ui/button"
import { LockKeyhole } from "lucide-react"

const Forbidden = () => {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gradient-to-b from-blue-50 to-blue-100 text-blue-900">
            <LockKeyhole className="w-24 h-24 text-blue-600 mb-8" />
            <h1 className="text-4xl font-bold mb-4">403 - Access Forbidden</h1>
            <p className="text-xl text-blue-700 mb-4">Sorry, you don't have permission to access this page.</p>
            <p className="text-lg text-blue-600 mb-8">This might be due to restricted course access or enrollment status.</p>
            <div className="space-x-4">
                <Button asChild className="bg-blue-600 hover:bg-blue-700 text-white">
                    <Link to="/dashboard">
                        Go to Dashboard
                    </Link>
                </Button>
                <Button asChild variant="outline" className="border-blue-600 text-blue-600 hover:bg-blue-100">
                    <Link to="/help">
                        Get Help
                    </Link>
                </Button>
            </div>
        </div>
    )
}

export default Forbidden
