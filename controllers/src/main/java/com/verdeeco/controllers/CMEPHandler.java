package com.verdeeco.controllers;

import com.verdeeco.cmep.CMEPRecordException;
import com.verdeeco.cmep.CMEPRecord;
import com.verdeeco.cmep.CMEPAlarm;
import com.verdeeco.models.device.DeviceAlarm;
import com.verdeeco.models.MongoDatastore;

public class CMEPHandler {

    public static int processCMEP(final String[] record, final String utility) throws CMEPRecordException {

        try {
            System.out.println(">>>>>>>>>>>>>>> record " + record[0]);

            CMEPRecord cmepRecord = CMEPRecord.getCMEPRecord(record, utility);
            CMEPRecord.RecordType recordType = cmepRecord.getRecordType();

            switch (recordType) {
                case MLA01:
                    System.out.println(">>>>>>>>>>>>>>> alarm count: " + cmepRecord.getAlarmsCount());
                    return saveAlarmRecord(cmepRecord);

                case MEPMD01:
                    //return saveIntervalRecord(cmepRecord);

                case MEPMD02:
                    //return saveTouRecord(cmepRecord);

                default:
                    throw new CMEPRecordException("Unsupported RecordType");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    protected static int saveAlarmRecord(final CMEPRecord record) {

        for (final CMEPAlarm a: record.getAlarms()) {
            final DeviceAlarm d = new DeviceAlarm(record.getMeterNumber(),
                                                    a.getAlarm().name(),
                                                    a.getEndTime());
            System.out.println(">>>>>>>>>>>>>>> alarm name: " + a.getAlarm().name());
            MongoDatastore.save(d);
        }

        return record.getAlarmsCount();
    }
}
