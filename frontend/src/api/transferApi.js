import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const transferClient = backendApi('/api/v1/transfer')

export const transferApi = {
    makeTransfer(token, transferRequest) {
        console.log(`Making transfer to account with number ${transferRequest.receiverAccountNumber}`);
        return transferClient.post("/makeTransfer", transferRequest, {
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getTransfers(token, pageNumber, pageSize) {
        console.log(`Getting transfers for a user`);
        return transferClient.get("/getTransfers", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getTransfersForProviderUser(token, pageNumber, pageSize) {
        console.log(`Getting transfers for a provider user`);
        return transferClient.get("/getTransfersForProvider", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getTransfersForReceiverUser(token, pageNumber, pageSize) {
        console.log(`Getting transfers for a receiver user`);
        return transferClient.get("/getTransfersForReceiver", {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    }
};