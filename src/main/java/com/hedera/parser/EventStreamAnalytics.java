package com.hedera.parser;

import com.hedera.databaseUtilities.DatabaseUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventStreamAnalytics {
	private static final Logger log = LogManager.getLogger("eventStream-log");
	private static final Marker MARKER = MarkerManager.getMarker("EVENT_STREAM");
	private static final Marker LOGM_EXCEPTION = MarkerManager.getMarker("EXCEPTION");
	private static Connection connect = null;

	public EventStreamAnalytics() {
		connect = DatabaseUtilities.openDatabase(connect);
	}

	/**
	 * Count the number of Created Events during the period
	 */
	public long getCreatedEventsCount(long periodStartInNanos, long periodEndInNanos) throws SQLException {
		PreparedStatement query = connect.prepareStatement(
				"SELECT COUNT(*) FROM t_events WHERE created_timestamp_ns >= ? AND created_timestamp_ns <= ?");
		query.setLong(1, periodStartInNanos);
		query.setLong(2, periodEndInNanos);
		query.execute();
		ResultSet resultSet = query.getResultSet();
		long count = 0;
		if (resultSet.next()) {
			count = resultSet.getLong(1);
		}
		resultSet.close();
		query.close();
		return count;
	}

	/**
	 * Count the number of Created Events created by a given node during the period
	 */
	public long getCreatedEventsCountForNode(long periodStartInNanos, long periodEndInNanos,
			long nodeId) throws SQLException {
		PreparedStatement query = connect.prepareStatement(
				"SELECT COUNT(*) FROM t_events WHERE created_timestamp_ns >= ? AND created_timestamp_ns <= ? AND " +
						"creator_node_id = ?");
		query.setLong(1, periodStartInNanos);
		query.setLong(2, periodEndInNanos);
		query.setLong(3, nodeId);
		query.execute();
		ResultSet resultSet = query.getResultSet();
		long count = 0;
		if (resultSet.next()) {
			count = resultSet.getLong(1);
		}
		resultSet.close();
		query.close();
		return count;
	}

	/**
	 * Count the number of Events which reach consensus during the period
	 */
	public long getConsensusEventsCount(long periodStartInNanos, long periodEndInNanos) throws SQLException {
		PreparedStatement query = connect.prepareStatement(
				"SELECT COUNT(*) FROM t_events WHERE consensus_timestamp_ns >= ? AND consensus_timestamp_ns <= ?");
		query.setLong(1, periodStartInNanos);
		query.setLong(2, periodEndInNanos);
		query.execute();
		ResultSet resultSet = query.getResultSet();
		long count = 0;
		if (resultSet.next()) {
			count = resultSet.getLong(1);
		}
		resultSet.close();
		query.close();
		return count;
	}

	/**
	 * Count the number of Events created by given node which reach consensus during the period
	 */
	public long getConsensusEventsCountForNode(long periodStartInNanos, long periodEndInNanos,
			long nodeId) throws SQLException {
		PreparedStatement query = connect.prepareStatement(
				"SELECT COUNT(*) FROM t_events WHERE consensus_timestamp_ns >= ? AND consensus_timestamp_ns <= ? AND " +
						"creator_node_id = ?");
		query.setLong(1, periodStartInNanos);
		query.setLong(2, periodEndInNanos);
		query.setLong(3, nodeId);
		query.execute();
		ResultSet resultSet = query.getResultSet();
		long count = 0;
		if (resultSet.next()) {
			count = resultSet.getLong(1);
		}
		resultSet.close();
		query.close();
		return count;
	}

	/**
	 * @param periodStartInNanos
	 * @param periodEndInNanos
	 * @param nodeId
	 * @return
	 * @throws SQLException
	 */
	public long[] getSysAppTxsCountInConsensusEventsForNode(long periodStartInNanos, long periodEndInNanos,
			long nodeId) throws SQLException {
		PreparedStatement query = connect.prepareStatement(
				"SELECT SUM(platform_tx_count), SUM(app_tx_count) FROM t_events WHERE consensus_timestamp_ns >= ? AND " +
						"consensus_timestamp_ns <= ? AND creator_node_id = ?");
		query.setLong(1, periodStartInNanos);
		query.setLong(2, periodEndInNanos);
		query.setLong(3, nodeId);
		query.execute();
		ResultSet resultSet = query.getResultSet();
		long[] count = new long[2];
		if (resultSet.next()) {
			count[0] = resultSet.getLong(1);
			count[1] = resultSet.getLong(2);
		}
		resultSet.close();
		query.close();
		return count;
	}

	public static void main(String[] args) throws SQLException {
		EventStreamAnalytics analytics = new EventStreamAnalytics();
		long periodStartInNanos = 1564084641196388000l;
		long periodEndInNanos = 1564084701826156000l;
		System.out.println(analytics.getCreatedEventsCount(periodStartInNanos, periodEndInNanos));
		long nodeId = 0;
		System.out.println(analytics.getCreatedEventsCountForNode(periodStartInNanos, periodEndInNanos, nodeId));

		periodStartInNanos = 1564084641217420000l;
		periodEndInNanos = 1564084701892894000l;
		System.out.println(analytics.getConsensusEventsCount(periodStartInNanos, periodEndInNanos));
		System.out.println(analytics.getConsensusEventsCountForNode(periodStartInNanos, periodEndInNanos, nodeId));

		periodStartInNanos = 1564084641217420000l;
		periodEndInNanos = 1564084701916832000l;
		long[] counts = analytics.getSysAppTxsCountInConsensusEventsForNode(periodStartInNanos, periodEndInNanos,
				nodeId);
		System.out.println(counts[0] + " " + counts[1]);
	}
}
