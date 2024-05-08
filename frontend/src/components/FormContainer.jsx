import {Container} from "@mui/material";

export const FormContainer = ({children, handleSubmit}) => {
    return <form onSubmit={handleSubmit}>
        <Container className="flex justify-center items-center h-screen">
            <Container className="w-1/2 text-white max-w-full bg-background p-8 rounded-lg shadow-lg border border-lightGray">
                {children}
            </Container>
        </Container>
    </form>
}