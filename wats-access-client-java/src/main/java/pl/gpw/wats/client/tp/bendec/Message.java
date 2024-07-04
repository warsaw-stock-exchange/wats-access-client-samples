package pl.gpw.wats.client.tp.bendec;

import java.math.BigInteger;
import java.util.*;
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
    
    static Optional<Message> createObject(byte[] bytes) {
        return createObject(getMsgType(bytes), bytes);
    }
    
    static Optional<Message> createObject(MsgType type, byte[] bytes) {
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
            case MASSQUOTE:
                return Optional.of(new MassQuote(bytes));
            case MASSQUOTERESPONSE:
                return Optional.of(new MassQuoteResponse(bytes));
            case MARKETMAKERCOMMAND:
                return Optional.of(new MarketMakerCommand(bytes));
            case MARKETMAKERCOMMANDRESPONSE:
                return Optional.of(new MarketMakerCommandResponse(bytes));
            case REQUESTFOREXECUTION:
                return Optional.of(new RequestForExecution(bytes));
            case ORDERMASSCANCEL:
                return Optional.of(new OrderMassCancel(bytes));
            case ORDERMASSCANCELRESPONSE:
                return Optional.of(new OrderMassCancelResponse(bytes));
            case BIDOFFERUPDATE:
                return Optional.of(new BidOfferUpdate(bytes));
            case TESTEVENT:
                return Optional.of(new TestEvent(bytes));
            default:
                return Optional.empty();
        }
    }
    
    static Class findClassByDiscriminator(MsgType type) {
        return  typeToClassMap.get(type);
    }
    
    static MsgType findDiscriminatorByClass(Class clazz) {
        return  classToTypeMap.get(clazz);
    }
    
    HashMap<Class, MsgType> classToTypeMap = new HashMap<>(){{
        put(Test.class, MsgType.TEST);
        put(Login.class, MsgType.LOGIN);
        put(LoginResponse.class, MsgType.LOGINRESPONSE);
        put(OrderAdd.class, MsgType.ORDERADD);
        put(OrderAddResponse.class, MsgType.ORDERADDRESPONSE);
        put(OrderCancel.class, MsgType.ORDERCANCEL);
        put(OrderCancelResponse.class, MsgType.ORDERCANCELRESPONSE);
        put(OrderModify.class, MsgType.ORDERMODIFY);
        put(OrderModifyResponse.class, MsgType.ORDERMODIFYRESPONSE);
        put(Trade.class, MsgType.TRADE);
        put(Logout.class, MsgType.LOGOUT);
        put(ConnectionClose.class, MsgType.CONNECTIONCLOSE);
        put(Heartbeat.class, MsgType.HEARTBEAT);
        put(LogoutResponse.class, MsgType.LOGOUTRESPONSE);
        put(Reject.class, MsgType.REJECT);
        put(TradeCaptureReportSingle.class, MsgType.TRADECAPTUREREPORTSINGLE);
        put(TradeCaptureReportDual.class, MsgType.TRADECAPTUREREPORTDUAL);
        put(TradeBust.class, MsgType.TRADEBUST);
        put(TradeCaptureReportResponse.class, MsgType.TRADECAPTUREREPORTRESPONSE);
        put(MassQuote.class, MsgType.MASSQUOTE);
        put(MassQuoteResponse.class, MsgType.MASSQUOTERESPONSE);
        put(MarketMakerCommand.class, MsgType.MARKETMAKERCOMMAND);
        put(MarketMakerCommandResponse.class, MsgType.MARKETMAKERCOMMANDRESPONSE);
        put(RequestForExecution.class, MsgType.REQUESTFOREXECUTION);
        put(OrderMassCancel.class, MsgType.ORDERMASSCANCEL);
        put(OrderMassCancelResponse.class, MsgType.ORDERMASSCANCELRESPONSE);
        put(BidOfferUpdate.class, MsgType.BIDOFFERUPDATE);
        put(TestEvent.class, MsgType.TESTEVENT);
    }};
    
    HashMap<MsgType, Class> typeToClassMap = new HashMap<>() {{
        put(MsgType.TEST, Test.class);
        put(MsgType.LOGIN, Login.class);
        put(MsgType.LOGINRESPONSE, LoginResponse.class);
        put(MsgType.ORDERADD, OrderAdd.class);
        put(MsgType.ORDERADDRESPONSE, OrderAddResponse.class);
        put(MsgType.ORDERCANCEL, OrderCancel.class);
        put(MsgType.ORDERCANCELRESPONSE, OrderCancelResponse.class);
        put(MsgType.ORDERMODIFY, OrderModify.class);
        put(MsgType.ORDERMODIFYRESPONSE, OrderModifyResponse.class);
        put(MsgType.TRADE, Trade.class);
        put(MsgType.LOGOUT, Logout.class);
        put(MsgType.CONNECTIONCLOSE, ConnectionClose.class);
        put(MsgType.HEARTBEAT, Heartbeat.class);
        put(MsgType.LOGOUTRESPONSE, LogoutResponse.class);
        put(MsgType.REJECT, Reject.class);
        put(MsgType.TRADECAPTUREREPORTSINGLE, TradeCaptureReportSingle.class);
        put(MsgType.TRADECAPTUREREPORTDUAL, TradeCaptureReportDual.class);
        put(MsgType.TRADEBUST, TradeBust.class);
        put(MsgType.TRADECAPTUREREPORTRESPONSE, TradeCaptureReportResponse.class);
        put(MsgType.MASSQUOTE, MassQuote.class);
        put(MsgType.MASSQUOTERESPONSE, MassQuoteResponse.class);
        put(MsgType.MARKETMAKERCOMMAND, MarketMakerCommand.class);
        put(MsgType.MARKETMAKERCOMMANDRESPONSE, MarketMakerCommandResponse.class);
        put(MsgType.REQUESTFOREXECUTION, RequestForExecution.class);
        put(MsgType.ORDERMASSCANCEL, OrderMassCancel.class);
        put(MsgType.ORDERMASSCANCELRESPONSE, OrderMassCancelResponse.class);
        put(MsgType.BIDOFFERUPDATE, BidOfferUpdate.class);
        put(MsgType.TESTEVENT, TestEvent.class);
    }};
}