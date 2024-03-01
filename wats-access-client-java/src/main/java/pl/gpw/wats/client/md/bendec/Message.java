package pl.gpw.wats.client.md.bendec;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    static Optional<Object> createObject(MsgType type, byte[] bytes){
        switch (type) {

            case HEARTBEAT:
                return Optional.of(new Heartbeat(bytes));

            case TEXT:
                return Optional.of(new Text(bytes));

            case TEST:
                return Optional.of(new Test(bytes));

            case ORDERADD:
                return Optional.of(new OrderAdd(bytes));

            case ORDERMODIFY:
                return Optional.of(new OrderModify(bytes));

            case ORDERDELETE:
                return Optional.of(new OrderDelete(bytes));

            case ORDEREXECUTE:
                return Optional.of(new OrderExecute(bytes));

            case STARTOFTECHNICALSESSION:
                return Optional.of(new StartOfTechnicalSession(bytes));

            case ENDOFTECHNICALSESSION:
                return Optional.of(new EndOfTechnicalSession(bytes));

            case REFERENCEDATASTART:
                return Optional.of(new ReferenceDataStart(bytes));

            case REFERENCEDATAEND:
                return Optional.of(new ReferenceDataEnd(bytes));

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

            case SESSIONSUMMARY:
                return Optional.of(new SessionSummary(bytes));

            case NEWS:
                return Optional.of(new News(bytes));

            case TESTEVENT:
                return Optional.of(new TestEvent(bytes));

            default:
                return Optional.empty();
        }
    }

}