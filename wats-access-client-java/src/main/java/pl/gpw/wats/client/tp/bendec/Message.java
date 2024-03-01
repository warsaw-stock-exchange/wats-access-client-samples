package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pl.gpw.wats.client.tp.bendec.MsgType;

import java.util.Optional;

public interface Message {
    default MsgType getMsgType() {
        return this.getHeader().getMsgType();
    }
    Header getHeader();

    static MsgType getMsgType(byte[] bytes) {
        return MsgType.getMsgType(bytes, 2);
    }

    static Optional<Object> createObject(MsgType type, byte[] bytes){
        switch (type) {

            case TEST:
                return Optional.of(new Test(bytes));

            case LOGIN:
                return Optional.of(new Login(bytes));

            case LOGINRESPONSE:
                return Optional.of(new LoginResponse(bytes));

            case ORDERADD:
                return Optional.of(new OrderAdd(bytes));

            case ORDERADDRESPONSE:
                return Optional.of(new OrderAddResponse(bytes));

            case ORDERCANCEL:
                return Optional.of(new OrderCancel(bytes));

            case ORDERCANCELRESPONSE:
                return Optional.of(new OrderCancelResponse(bytes));

            case ORDERMODIFY:
                return Optional.of(new OrderModify(bytes));

            case ORDERMODIFYRESPONSE:
                return Optional.of(new OrderModifyResponse(bytes));

            case TRADE:
                return Optional.of(new Trade(bytes));

            case LOGOUT:
                return Optional.of(new Logout(bytes));

            case CONNECTIONCLOSE:
                return Optional.of(new ConnectionClose(bytes));

            case HEARTBEAT:
                return Optional.of(new Heartbeat(bytes));

            case LOGOUTRESPONSE:
                return Optional.of(new LogoutResponse(bytes));

            case REJECT:
                return Optional.of(new Reject(bytes));

            case TRADECAPTUREREPORTSINGLE:
                return Optional.of(new TradeCaptureReportSingle(bytes));

            case TRADECAPTUREREPORTDUAL:
                return Optional.of(new TradeCaptureReportDual(bytes));

            case TRADEBUST:
                return Optional.of(new TradeBust(bytes));

            case TRADECAPTUREREPORTRESPONSE:
                return Optional.of(new TradeCaptureReportResponse(bytes));

            case RISKLIMITDEFINITION:
                return Optional.of(new RiskLimitDefinition(bytes));

            case RISKLIMITDEFINITIONRESPONSE:
                return Optional.of(new RiskLimitDefinitionResponse(bytes));

            case RISKLIMITBREACH:
                return Optional.of(new RiskLimitBreach(bytes));

            case MASSQUOTE:
                return Optional.of(new MassQuote(bytes));

            case MASSQUOTERESPONSE:
                return Optional.of(new MassQuoteResponse(bytes));

            case INITIATESTATE:
                return Optional.of(new InitiateState(bytes));

            case REQUESTFOREXECUTION:
                return Optional.of(new RequestForExecution(bytes));

            case TESTEVENT:
                return Optional.of(new TestEvent(bytes));

            default:
                return Optional.empty();
        }
    }

}