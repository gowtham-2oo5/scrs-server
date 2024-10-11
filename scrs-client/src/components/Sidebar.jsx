import React, { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Accordion, AccordionItem, AccordionTrigger, AccordionContent } from "@/components/ui/accordion";

const Sidebar = ({ className, sidebarItems, singleItems }) => {
    const [openItem, setOpenItem] = useState(null);  // Track which accordion item is open
    const [selectedSubItem, setSelectedSubItem] = useState("");  // Track selected sub item
    const location = useLocation();  // Get the current location

    const handleAccordionToggle = (index) => {
        setOpenItem(openItem === index ? null : index);  // Toggle the opened state
    };

    const handleSubItemClick = (path) => {
        setSelectedSubItem(path);  // Set the selected sub item
    };

    // Effect to collapse all accordions when navigating to root path
    useEffect(() => {
        if (location.pathname === "/admin") {
            setOpenItem(null);  // Collapse all accordions
            setSelectedSubItem("");  // Reset selected sub item if desired
        }
    }, [location.pathname]);

    return (
        <Card className={`h-full rounded-none border-r ${className}`}>
            <CardHeader>
                <CardTitle style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
                    <Link to="">
                        <div className="h-10 w-10 bg-blue-gray-200 self-center rounded-sm"></div>
                    </Link>
                </CardTitle>
            </CardHeader>
            <ScrollArea className="h-[calc(100vh-5rem)]">
                <CardContent>
                    <Accordion type="single" collapsible value={openItem} onValueChange={setOpenItem} className="w-full">
                        {sidebarItems.map((item, index) => (
                            <AccordionItem value={`item-${index}`} key={index}>
                                <AccordionTrigger onClick={() => handleAccordionToggle(index)}>
                                    <div className="flex items-center gap-2">
                                        {React.createElement(item.icon, { className: "h-4 w-4" })}
                                        {item.label}
                                    </div>
                                </AccordionTrigger>
                                <AccordionContent>
                                    <div className="flex flex-col space-y-2 pl-6">
                                        {Object.entries(item.items).map(([subItemLabel, subItemPath], subIndex) => (
                                            <Button
                                                key={subIndex}
                                                variant="ghost"  // Use ghost variant for sub-items
                                                className={`justify-start gap-2 ${selectedSubItem === subItemPath ? 'underline' : ''}`}  // Underline for selected
                                                onClick={() => handleSubItemClick(subItemPath)}  // Handle sub item click
                                                asChild  // Enables the Link to be treated as the Button child
                                            >
                                                <Link to={subItemPath}>
                                                    {subItemLabel}
                                                </Link>
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
                                asChild
                            >
                                <Link to={item.path}>
                                    {React.createElement(item.icon, { className: "h-4 w-4" })}
                                    {item.label}
                                </Link>
                            </Button>
                        ))}
                    </div>
                </CardContent>
            </ScrollArea>
        </Card>
    );
}

export default Sidebar;
