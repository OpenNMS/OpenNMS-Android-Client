package org.opennms.android.net;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.opennms.android.net.Client;
import org.opennms.android.net.Response;
import org.opennms.android.parsing.OutagesParser;
import org.opennms.android.provider.Contract;

import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    private static final String TAG = "DataLoader";
    private ContentResolver contentResolver;
    private Client serverCommunication;

    public DataLoader(Context context) {
        contentResolver = context.getContentResolver();
        serverCommunication = new Client(context);
    }

    public Response loadNodes(int limit) throws IOException {
        return loadNodes(limit, 0);
    }

    public Response loadNodes(int limit, int offset) throws IOException {
        return serverCommunication.get(String.format("nodes?limit=%d&offset=%d", limit, offset));
    }

    public Response loadEvents(int limit) throws IOException {
        return loadEvents(limit, 0);
    }

    public Response loadEvents(int limit, int offset) throws IOException {
        return serverCommunication.get(String.format("events?orderBy=id&order=desc&limit=%d&offset=%d", limit, offset));
    }

    public Response loadAlarms(int limit) throws IOException {
        return loadAlarms(limit, 0);
    }

    public Response loadAlarms(int limit, int offset) throws IOException {
        return serverCommunication.get(String.format("alarms?limit=%d&offset=%d", limit, offset));
    }

    public Response loadOutages(int limit) throws IOException {
        return loadOutages(limit, 0);
    }

    public Response loadOutages(int limit, int offset) throws IOException {
        return serverCommunication.get(String.format("outages?limit=%d&offset=%d", limit, offset));
    }


    public ArrayList<ContentValues> loadCurrentOutages() {
        Log.d(TAG, "Loading current outages...");
        String result;
        try {
            result = serverCommunication
                    .get("outages?ifRegainedService=null&comparator=eq").getMessage();
        } catch (Exception e) {
            Log.e(TAG, "Error occurred during outages loading process", e);
            return null;
        }
        contentResolver.delete(Contract.Outages.CONTENT_URI, null, null);
        ArrayList<ContentValues> values = OutagesParser.parseMultiple(result);
        contentResolver.bulkInsert(Contract.Outages.CONTENT_URI,
                values.toArray(new ContentValues[values.size()]));
        Log.d(TAG, "Outage loading complete.");
        return values;
    }


}