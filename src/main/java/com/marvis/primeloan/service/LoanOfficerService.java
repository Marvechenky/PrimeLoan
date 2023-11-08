package com.marvis.primeloan.service;
import com.marvis.primeloan.data.dto.request.*;
import com.marvis.primeloan.data.dto.response.AuthResponse;
import com.marvis.primeloan.data.dto.response.Response;
import com.marvis.primeloan.data.dto.response.ReviewLoanApplicationResponse;
import com.marvis.primeloan.data.model.Loan;



import java.util.List;

public interface LoanOfficerService {

    List<Loan> viewLoanApplications();

    ReviewLoanApplicationResponse reviewLoanApplicationDetails(ReviewLoanApplicationRequest loanApplicationRequest);
    Response approveLoanApplication(ViewLoanApplicationRequest viewLoanApplicationStatus);
    Response rejectLoanApplication(RejectLoanApplicationRequest rejectLoanApplicationRequest);
    Response generateLoanAgreement(GenerateLoanAgreementRequest generateLoanAgreementRequest);
    Response updateLoanApplicationStatus(UpdateLoanApplicationStatusRequest updateLoanApplicationStatusRequest);

    AuthResponse authenticateAndGetToken(AuthRequest authRequest);

}
