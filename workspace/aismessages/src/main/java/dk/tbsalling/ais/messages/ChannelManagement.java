package dk.tbsalling.ais.messages;

import dk.tbsalling.ais.Decoder;
import dk.tbsalling.ais.exceptions.InvalidEncodedMessage;
import dk.tbsalling.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.ais.messages.types.AISMessageType;
import dk.tbsalling.ais.messages.types.MMSI;
import dk.tbsalling.ais.messages.types.TxRxMode;

@SuppressWarnings("serial")
public class ChannelManagement extends DecodedAISMessage {

	public ChannelManagement(
			Integer repeatIndicator, MMSI sourceMmsi, Integer channelA,
			Integer channelB, TxRxMode transmitReceiveMode, Boolean power,
			Float longitudeNorthEast, Float northEastLatitude,
			Float southWestLongitude, Float southWestLatitude,
			MMSI destinationMmsi1, MMSI destinationMmsi2, Boolean addressed,
			Boolean bandA, Boolean bandB, Integer zoneSize) {
		super(AISMessageType.ChannelManagement, repeatIndicator, sourceMmsi);
		this.channelA = channelA;
		this.channelB = channelB;
		this.transmitReceiveMode = transmitReceiveMode;
		this.power = power;
		this.northEastLongitude = longitudeNorthEast;
		this.northEastLatitude = northEastLatitude;
		this.southWestLongitude = southWestLongitude;
		this.southWestLatitude = southWestLatitude;
		this.destinationMmsi1 = destinationMmsi1;
		this.destinationMmsi2 = destinationMmsi2;
		this.addressed = addressed;
		this.bandA = bandA;
		this.bandB = bandB;
		this.zoneSize = zoneSize;
	}

	public final Integer getChannelA() {
		return channelA;
	}

	public final Integer getChannelB() {
		return channelB;
	}

	public final TxRxMode getTransmitReceiveMode() {
		return transmitReceiveMode;
	}

	public final Boolean getPower() {
		return power;
	}

	public final Float getNorthEastLongitude() {
		return northEastLongitude;
	}

	public final Float getNorthEastLatitude() {
		return northEastLatitude;
	}

	public final Float getSouthWestLongitude() {
		return southWestLongitude;
	}

	public final Float getSouthWestLatitude() {
		return southWestLatitude;
	}

	public final MMSI getDestinationMmsi1() {
		return destinationMmsi1;
	}

	public final MMSI getDestinationMmsi2() {
		return destinationMmsi2;
	}

	public final Boolean getAddressed() {
		return addressed;
	}

	public final Boolean getBandA() {
		return bandA;
	}

	public final Boolean getBandB() {
		return bandB;
	}

	public final Integer getZoneSize() {
		return zoneSize;
	}

	public static ChannelManagement fromEncodedMessage(EncodedAISMessage encodedMessage) {
		if (! encodedMessage.isValid())
			throw new InvalidEncodedMessage(encodedMessage);
		if (! encodedMessage.getMessageType().equals(AISMessageType.ChannelManagement))
			throw new UnsupportedMessageType(encodedMessage.getMessageType().getCode());
			
		Integer repeatIndicator = Decoder.convertToUnsignedInteger(encodedMessage.getBits(6, 8));
		MMSI sourceMmsi = MMSI.valueOf(Decoder.convertToUnsignedLong(encodedMessage.getBits(8, 38)));
		
		Integer channelA = Decoder.convertToUnsignedInteger(encodedMessage.getBits(40, 52));
		Integer channelB = Decoder.convertToUnsignedInteger(encodedMessage.getBits(52, 64));
		TxRxMode transmitReceiveMode = TxRxMode.fromInteger(Decoder.convertToUnsignedInteger(encodedMessage.getBits(64, 68)));
		Boolean power = Decoder.convertToBoolean(encodedMessage.getBits(68, 69));
		Boolean addressed = Decoder.convertToBoolean(encodedMessage.getBits(139, 140));
		Boolean bandA = Decoder.convertToBoolean(encodedMessage.getBits(140, 141));
		Boolean bandB = Decoder.convertToBoolean(encodedMessage.getBits(141, 142));
		Integer zoneSize = Decoder.convertToUnsignedInteger(encodedMessage.getBits(142, 145));
		MMSI destinationMmsi1 = !addressed ? null : MMSI.valueOf(Decoder.convertToUnsignedLong(encodedMessage.getBits(69, 99)));
		MMSI destinationMmsi2 = !addressed ? null : MMSI.valueOf(Decoder.convertToUnsignedLong(encodedMessage.getBits(104, 134)));
		Float northEastLatitude = addressed ? null : (float) (Decoder.convertToSignedInteger(encodedMessage.getBits(87, 104)) / 10);
		Float northEastLongitude = addressed ? null : (float) (Decoder.convertToSignedInteger(encodedMessage.getBits(69, 87)) / 10);
		Float southWestLatitude = addressed ? null : (float) (Decoder.convertToSignedInteger(encodedMessage.getBits(122, 138)) / 10);
		Float southWestLongitude = addressed ? null : (float) (Decoder.convertToSignedInteger(encodedMessage.getBits(104, 122)) / 10);

		return new ChannelManagement(repeatIndicator, sourceMmsi, channelA,
				channelB, transmitReceiveMode, power, northEastLongitude,
				northEastLatitude, southWestLongitude, southWestLatitude,
				destinationMmsi1, destinationMmsi2, addressed, bandA, bandB,
				zoneSize);
	}
		
	private final Integer channelA;
	private final Integer channelB;
	private final TxRxMode transmitReceiveMode;
	private final Boolean power;
	private final Float northEastLongitude;
	private final Float northEastLatitude;
	private final Float southWestLongitude;
	private final Float southWestLatitude;
	private final MMSI destinationMmsi1;
	private final MMSI destinationMmsi2;
	private final Boolean addressed;
	private final Boolean bandA;
	private final Boolean bandB;
	private final Integer zoneSize;
}
