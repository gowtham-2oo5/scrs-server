import React from 'react'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Accordion, AccordionItem, AccordionTrigger, AccordionContent } from "@/components/ui/accordion"

const Sidebar = ({ className, sidebarItems, singleItems }) => (
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

export default Sidebar;

