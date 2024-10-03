import React from 'react'
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import logo from "./assets/backViewOfGirlWorkingOnLaptop.png"
import { Link } from 'react-router-dom'


const App = () => {
  return (
    <div className="flex flex-col min-h-screen bg-blue-50">
      <header className="flex justify-between items-center p-4">
        <a href="/" className="text-2xl font-bold text-blue-600">
          KLU - CRP
        </a>
        <Link to="/login">
          <Button variant="default" className="bg-blue-600 hover:bg-blue-700">
            LOGIN
          </Button>
        </Link>
      </header>

      <main className="flex-grow flex items-center px-4 py-12">
        <div className="container mx-auto flex flex-col md:flex-row items-center justify-between gap-8">
          <div className="md:w-1/2">
            <h1 className="text-4xl md:text-5xl font-bold mb-4 text-gray-800">
              Seamless Course Registration & Scheduling Made Easy –{' '}
              <span className="text-blue-600">Start Your Journey Now!</span>
            </h1>
          </div>
          <div className="md:w-1/2">
            <Card className="bg-transparent">
              <CardContent className="p-0 bg-transparent">
                <img
                  src={logo}
                  alt="Student working at desk"
                  className="w-full h-auto rounded-lg"
                />
              </CardContent>
            </Card>

          </div>
        </div>
      </main>

      <footer className="p-4 text-center text-gray-600 ">
        <div className="flex justify-center">
          <p>&copy; {new Date().getFullYear()}</p>
        </div>
      </footer>


    </div>
  )
}

export default App;