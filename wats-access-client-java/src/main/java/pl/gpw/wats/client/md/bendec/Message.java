package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.*;
import pl.gpw.wats.client.md.bendec.MsgType;
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
            case HEARTBEAT:
                return Optional.of(new Heartbeat(bytes));
            case ORDERADD:
                return Optional.of(new OrderAdd(bytes));
            case ORDERMODIFY:
                return Optional.of(new OrderModify(bytes));
            case ORDERCANCEL:
                return Optional.of(new OrderCancel(bytes));
            case ORDEREXECUTE:
                return Optional.of(new OrderExecute(bytes));
            case TRADINGSESSIONSTATUS:
                return Optional.of(new TradingSessionStatus(bytes));
            case ENCRYPTIONKEY:
                return Optional.of(new EncryptionKey(bytes));
            case INSTRUMENTSTATUSCHANGE:
                return Optional.of(new InstrumentStatusChange(bytes));
            case TRADINGPHASESCHEDULEENTRY:
                return Optional.of(new TradingPhaseScheduleEntry(bytes));
            case TICKTABLEENTRY:
                return Optional.of(new TickTableEntry(bytes));
            case WEEKPLAN:
                return Optional.of(new WeekPlan(bytes));
            case CALENDAREXCEPTION:
                return Optional.of(new CalendarException(bytes));
            case ACCRUEDINTERESTTABLEENTRY:
                return Optional.of(new AccruedInterestTableEntry(bytes));
            case INDEXATIONTABLEENTRY:
                return Optional.of(new IndexationTableEntry(bytes));
            case TRADE:
                return Optional.of(new Trade(bytes));
            case COLLARTABLEENTRY:
                return Optional.of(new CollarTableEntry(bytes));
            case TOPPRICELEVELUPDATE:
                return Optional.of(new TopPriceLevelUpdate(bytes));
            case PRICELEVELSNAPSHOT:
                return Optional.of(new PriceLevelSnapshot(bytes));
            case AUCTIONUPDATE:
                return Optional.of(new AuctionUpdate(bytes));
            case PRICEUPDATE:
                return Optional.of(new PriceUpdate(bytes));
            case TRADECOLLARS:
                return Optional.of(new TradeCollars(bytes));
            case ORDERCOLLARS:
                return Optional.of(new OrderCollars(bytes));
            case AUCTIONSUMMARY:
                return Optional.of(new AuctionSummary(bytes));
            case MARKETSTRUCTURE:
                return Optional.of(new MarketStructure(bytes));
            case INSTRUMENT:
                return Optional.of(new Instrument(bytes));
            case COLLARGROUP:
                return Optional.of(new CollarGroup(bytes));
            case LOGIN:
                return Optional.of(new Login(bytes));
            case LOGINRESPONSE:
                return Optional.of(new LoginResponse(bytes));
            case LOGOUT:
                return Optional.of(new Logout(bytes));
            case ENDOFSNAPSHOT:
                return Optional.of(new EndOfSnapshot(bytes));
            case ORDERBOOKEVENT:
                return Optional.of(new OrderBookEvent(bytes));
            case REALTIMEINDEX:
                return Optional.of(new RealTimeIndex(bytes));
            case INDEXSUMMARY:
                return Optional.of(new IndexSummary(bytes));
            case INDEXPORTFOLIOENTRY:
                return Optional.of(new IndexPortfolioEntry(bytes));
            case INDEXPARAMS:
                return Optional.of(new IndexParams(bytes));
            case INSTRUMENTSUMMARY:
                return Optional.of(new InstrumentSummary(bytes));
            case PRODUCTSUMMARY:
                return Optional.of(new ProductSummary(bytes));
            case NEWS:
                return Optional.of(new News(bytes));
            case TESTEVENT:
                return Optional.of(new TestEvent(bytes));
            case POSITIONREPORT:
                return Optional.of(new PositionReport(bytes));
            case EXTERNALUNDERLYING:
                return Optional.of(new ExternalUnderlying(bytes));
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
        put(Heartbeat.class, MsgType.HEARTBEAT);
        put(OrderAdd.class, MsgType.ORDERADD);
        put(OrderModify.class, MsgType.ORDERMODIFY);
        put(OrderCancel.class, MsgType.ORDERCANCEL);
        put(OrderExecute.class, MsgType.ORDEREXECUTE);
        put(TradingSessionStatus.class, MsgType.TRADINGSESSIONSTATUS);
        put(EncryptionKey.class, MsgType.ENCRYPTIONKEY);
        put(InstrumentStatusChange.class, MsgType.INSTRUMENTSTATUSCHANGE);
        put(TradingPhaseScheduleEntry.class, MsgType.TRADINGPHASESCHEDULEENTRY);
        put(TickTableEntry.class, MsgType.TICKTABLEENTRY);
        put(WeekPlan.class, MsgType.WEEKPLAN);
        put(CalendarException.class, MsgType.CALENDAREXCEPTION);
        put(AccruedInterestTableEntry.class, MsgType.ACCRUEDINTERESTTABLEENTRY);
        put(IndexationTableEntry.class, MsgType.INDEXATIONTABLEENTRY);
        put(Trade.class, MsgType.TRADE);
        put(CollarTableEntry.class, MsgType.COLLARTABLEENTRY);
        put(TopPriceLevelUpdate.class, MsgType.TOPPRICELEVELUPDATE);
        put(PriceLevelSnapshot.class, MsgType.PRICELEVELSNAPSHOT);
        put(AuctionUpdate.class, MsgType.AUCTIONUPDATE);
        put(PriceUpdate.class, MsgType.PRICEUPDATE);
        put(TradeCollars.class, MsgType.TRADECOLLARS);
        put(OrderCollars.class, MsgType.ORDERCOLLARS);
        put(AuctionSummary.class, MsgType.AUCTIONSUMMARY);
        put(MarketStructure.class, MsgType.MARKETSTRUCTURE);
        put(Instrument.class, MsgType.INSTRUMENT);
        put(CollarGroup.class, MsgType.COLLARGROUP);
        put(Login.class, MsgType.LOGIN);
        put(LoginResponse.class, MsgType.LOGINRESPONSE);
        put(Logout.class, MsgType.LOGOUT);
        put(EndOfSnapshot.class, MsgType.ENDOFSNAPSHOT);
        put(OrderBookEvent.class, MsgType.ORDERBOOKEVENT);
        put(RealTimeIndex.class, MsgType.REALTIMEINDEX);
        put(IndexSummary.class, MsgType.INDEXSUMMARY);
        put(IndexPortfolioEntry.class, MsgType.INDEXPORTFOLIOENTRY);
        put(IndexParams.class, MsgType.INDEXPARAMS);
        put(InstrumentSummary.class, MsgType.INSTRUMENTSUMMARY);
        put(ProductSummary.class, MsgType.PRODUCTSUMMARY);
        put(News.class, MsgType.NEWS);
        put(TestEvent.class, MsgType.TESTEVENT);
        put(PositionReport.class, MsgType.POSITIONREPORT);
        put(ExternalUnderlying.class, MsgType.EXTERNALUNDERLYING);
    }};
    
    HashMap<MsgType, Class> typeToClassMap = new HashMap<>() {{
        put(MsgType.HEARTBEAT, Heartbeat.class);
        put(MsgType.ORDERADD, OrderAdd.class);
        put(MsgType.ORDERMODIFY, OrderModify.class);
        put(MsgType.ORDERCANCEL, OrderCancel.class);
        put(MsgType.ORDEREXECUTE, OrderExecute.class);
        put(MsgType.TRADINGSESSIONSTATUS, TradingSessionStatus.class);
        put(MsgType.ENCRYPTIONKEY, EncryptionKey.class);
        put(MsgType.INSTRUMENTSTATUSCHANGE, InstrumentStatusChange.class);
        put(MsgType.TRADINGPHASESCHEDULEENTRY, TradingPhaseScheduleEntry.class);
        put(MsgType.TICKTABLEENTRY, TickTableEntry.class);
        put(MsgType.WEEKPLAN, WeekPlan.class);
        put(MsgType.CALENDAREXCEPTION, CalendarException.class);
        put(MsgType.ACCRUEDINTERESTTABLEENTRY, AccruedInterestTableEntry.class);
        put(MsgType.INDEXATIONTABLEENTRY, IndexationTableEntry.class);
        put(MsgType.TRADE, Trade.class);
        put(MsgType.COLLARTABLEENTRY, CollarTableEntry.class);
        put(MsgType.TOPPRICELEVELUPDATE, TopPriceLevelUpdate.class);
        put(MsgType.PRICELEVELSNAPSHOT, PriceLevelSnapshot.class);
        put(MsgType.AUCTIONUPDATE, AuctionUpdate.class);
        put(MsgType.PRICEUPDATE, PriceUpdate.class);
        put(MsgType.TRADECOLLARS, TradeCollars.class);
        put(MsgType.ORDERCOLLARS, OrderCollars.class);
        put(MsgType.AUCTIONSUMMARY, AuctionSummary.class);
        put(MsgType.MARKETSTRUCTURE, MarketStructure.class);
        put(MsgType.INSTRUMENT, Instrument.class);
        put(MsgType.COLLARGROUP, CollarGroup.class);
        put(MsgType.LOGIN, Login.class);
        put(MsgType.LOGINRESPONSE, LoginResponse.class);
        put(MsgType.LOGOUT, Logout.class);
        put(MsgType.ENDOFSNAPSHOT, EndOfSnapshot.class);
        put(MsgType.ORDERBOOKEVENT, OrderBookEvent.class);
        put(MsgType.REALTIMEINDEX, RealTimeIndex.class);
        put(MsgType.INDEXSUMMARY, IndexSummary.class);
        put(MsgType.INDEXPORTFOLIOENTRY, IndexPortfolioEntry.class);
        put(MsgType.INDEXPARAMS, IndexParams.class);
        put(MsgType.INSTRUMENTSUMMARY, InstrumentSummary.class);
        put(MsgType.PRODUCTSUMMARY, ProductSummary.class);
        put(MsgType.NEWS, News.class);
        put(MsgType.TESTEVENT, TestEvent.class);
        put(MsgType.POSITIONREPORT, PositionReport.class);
        put(MsgType.EXTERNALUNDERLYING, ExternalUnderlying.class);
    }};
}