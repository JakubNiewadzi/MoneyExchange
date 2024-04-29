import {Container} from "@mui/material";

export const FormContainer = ({children, handleSubmit}) => {
    return <form onSubmit={handleSubmit}>
        <Container className="flex justify-center items-center h-screen">
            <Container className="w-1/2 max-w-full bg-gray-200 p-8 rounded-sm shadow-md">
                {children}
            </Container>
        </Container>
    </form>
}