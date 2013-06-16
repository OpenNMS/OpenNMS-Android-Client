package org.opennms.android.ui.events;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import org.opennms.android.R;
import org.opennms.android.dao.events.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetailsFragment extends SherlockFragment {
    private static final String TAG = "EventDetailsFragment";
    Event event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateContent();
    }

    public void bindEvent(Event event) {
        this.event = event;
        if (this.isVisible()) updateContent();
    }

    public void updateContent() {
        if (event != null) {
            // Event ID
            TextView id = (TextView) getActivity().findViewById(R.id.event_id);
            id.setText(getString(R.string.event_details_id) + event.getId());

            // Severity
            TextView severity = (TextView) getActivity().findViewById(R.id.event_severity);
            severity.setText(String.valueOf(event.getSeverity()));
            TableRow severityRow = (TableRow) getActivity().findViewById(R.id.event_severity_row);
            if (event.getSeverity().equals("CLEARED")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_cleared));
            } else if (event.getSeverity().equals("MINOR")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_minor));
            } else if (event.getSeverity().equals("NORMAL")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_normal));
            } else if (event.getSeverity().equals("INDETERMINATE")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_minor));
            } else if (event.getSeverity().equals("WARNING")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_warning));
            } else if (event.getSeverity().equals("MAJOR")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_major));
            } else if (event.getSeverity().equals("CRITICAL")) {
                severityRow.setBackgroundColor(getResources().getColor(R.color.severity_critical));
            }

            // Creation time
            TextView timeTextView = (TextView) getActivity().findViewById(R.id.event_create_time);
            // Example: "2011-09-27T12:15:32.363-04:00"
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String tileString = event.getCreateTime();
            if (tileString != null) {
                try {
                    Date createTime = format.parse(event.getCreateTime());
                    timeTextView.setText(createTime.toString());
                } catch (ParseException e) {
                    Log.e(TAG, "Creation time parsing error");
                }
            }

            // Log message
            TextView logMessage = (TextView) getActivity().findViewById(R.id.event_log_message);
            logMessage.setText(event.getLogMessage());

            // Description
            TextView description = (TextView) getActivity().findViewById(R.id.event_description);
            description.setText(Html.fromHtml(event.getDescription()));

            // Host
            TextView host = (TextView) getActivity().findViewById(R.id.event_host);
            host.setText("Host: " + event.getHost());

            // IP address
            TextView ipAddress = (TextView) getActivity().findViewById(R.id.event_ip_address);
            ipAddress.setText("IP address: " + event.getIpAddress());

            // Node ID
            TextView nodeId = (TextView) getActivity().findViewById(R.id.event_node_id);
            nodeId.setText("Node ID: " + event.getNodeId());

            // Node label
            TextView nodeLabel = (TextView) getActivity().findViewById(R.id.event_node_label);
            nodeLabel.setText("Node label: " + event.getNodeLabel());
        }
    }

}