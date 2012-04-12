package org.cloudsherpa.ui.server;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cloudsherpa.ui.client.UIService;
import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.NovaFlavor;
import org.openstack.model.compute.nova.NovaServer;
import org.openstack.model.compute.nova.floatingip.NovaFloatingIp;
import org.openstack.model.compute.nova.keypair.NovaKeyPair;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroup;
import org.openstack.model.compute.nova.snapshot.NovaSnapshot;
import org.openstack.model.compute.nova.volume.NovaVolume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.User;
import org.openstack.model.identity.keystone.KeystoneEndpoint;
import org.openstack.model.identity.keystone.KeystoneRole;
import org.openstack.model.identity.keystone.KeystoneService;
import org.openstack.model.identity.keystone.KeystoneTenant;
import org.openstack.model.identity.keystone.KeystoneUser;
import org.openstack.model.images.Image;
import org.openstack.model.images.glance.GlanceImage;

import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UIMockServiceImpl extends RemoteServiceServlet implements UIService {
	
	private static Map<String, Server> servers = Maps.newConcurrentMap();
	private static Map<String, Flavor> flavors = Maps.newConcurrentMap();
	private static Map<String, Image> images = Maps.newConcurrentMap();
	private static Map<Integer, FloatingIp> floatingIps = Maps.newConcurrentMap();
	private static Map<Integer, Volume> volumes = Maps.newConcurrentMap();
	private static Map<Integer, Snapshot> snapshots = Maps.newConcurrentMap();
	private static Map<String, KeyPair> keyPairs = Maps.newConcurrentMap();
	private static Map<Integer, SecurityGroup> securityGroups = Maps.newConcurrentMap();
	
	private static Map<String, Tenant> tenants = Maps.newConcurrentMap();
	private static Map<String, User> users = Maps.newConcurrentMap();
	private static Map<String, Role> roles = Maps.newConcurrentMap();
	private static Map<Integer, Service> services = Maps.newConcurrentMap();
	private static Map<Integer, Endpoint> endpoints = Maps.newConcurrentMap();
	
	static {
		for(int i = 0; i < 3; i++) {
			images.put(String.valueOf(i), new GlanceImage(String.valueOf(i),"image."+i));
			flavors.put(String.valueOf(i), new NovaFlavor(String.valueOf(i),"flavor."+i));
			servers.put(String.valueOf(i), new NovaServer(String.valueOf(i),"server."+i));
			floatingIps.put(i, new NovaFloatingIp(i, String.valueOf(i), String.valueOf(i), "nova", String.valueOf(i)));
			volumes.put(i, new NovaVolume(i, "volume."+i));
			snapshots.put(i, new NovaSnapshot());
			keyPairs.put("key."+i, new NovaKeyPair("key."+i));
			securityGroups.put(i, new NovaSecurityGroup(i,"security group."+i));
			
			
			tenants.put(String.valueOf(i), new KeystoneTenant(String.valueOf(i),"tenant."+i));
			users.put(String.valueOf(i), new KeystoneUser(String.valueOf(i),"user."+i));
			roles.put(String.valueOf(i), new KeystoneRole(String.valueOf(i),"role."+i));
			services.put(i, new KeystoneService(String.valueOf(i), "service."+i, "compute", "desc"));
			endpoints.put(i, new KeystoneEndpoint(String.valueOf(i), "nova", String.valueOf(i)));
			
			
		}
	}
	

	@Override
	public List<Server> listServers(int start, int max) {
		return Lists.newArrayList(servers.values());
	}

	@Override
	public List<Server> createServer(ServerForCreate serverForCreate) {
		return listServers(0, 10);
	}

	@Override
	public List<Server> deleteServers(Collection<String> ids) {
		return listServers(0, 10);
	}

	@Override
	public Server executeServerAction(Collection<String> id, ServerAction action) {
		return new NovaServer();
	}

	@Override
	public List<Flavor> listFlavors(int start, int max) {
		return Lists.newArrayList(flavors.values());
	}

	@Override
	public List<Flavor> createFlavor(Flavor flavor) {
		return listFlavors(0, 10);
	}

	@Override
	public List<Flavor> deleteFlavors(Collection<String> ids) {
		return listFlavors(0, 10);
	}

	@Override
	public List<Image> listImages(int starts, int max) {
		return Lists.newArrayList(images.values());
	}

	@Override
	public List<Image> createImage(Image image) {
		return listImages(0, 10);
	}

	@Override
	public List<Image> deleteImages(Collection<String> ids) {
		return listImages(0, 10);
	}

	@Override
	public List<FloatingIp> listFloatingIps() {
		return Lists.newArrayList(floatingIps.values());
	}

	@Override
	public List<FloatingIp> createFloatingIp() {
		return listFloatingIps();
	}

	@Override
	public List<FloatingIp> deleteFloatingIps(Collection<Integer> ids) {
		return listFloatingIps();
	}

	@Override
	public List<FloatingIp> associateFloatingIp(String ip, String serverId) {
		return listFloatingIps();
	}

	@Override
	public List<FloatingIp> disassociateFloatingIp(String ip) {
		return listFloatingIps();
	}

	@Override
	public List<Volume> listVolumes() {
		return Lists.newArrayList(volumes.values());
	}

	@Override
	public List<Volume> createVolume(VolumeForCreate volumeForCreate) {
		return listVolumes();
	}

	@Override
	public List<Volume> deleteVolumes(Collection<Integer> ids) {
		return listVolumes();
	}

	@Override
	public List<Volume> attachVolume() {
		return listVolumes();
	}

	@Override
	public List<Volume> detachVolumes(Integer id, String serverId) {
		return listVolumes();
	}

	@Override
	public List<Snapshot> listSnapshots() {
		return Lists.newArrayList(snapshots.values());
	}

	@Override
	public List<Snapshot> createSnapshots(SnapshotForCreate snapshotForCreate) {
		return listSnapshots();
	}

	@Override
	public List<Snapshot> deleteSnapshots(Collection<Integer> ids) {
		return listSnapshots();
	}

	@Override
	public List<KeyPair> listKeyPairs() {
		return Lists.newArrayList(keyPairs.values());
	}

	@Override
	public KeyPair createKeyPair(String name) {
		return new NovaKeyPair();
	}

	@Override
	public List<KeyPair> deleteKeyPairs(Collection<String> names) {
		return listKeyPairs();
	}

	@Override
	public List<SecurityGroup> listSecurityGroups() {
		return Lists.newArrayList(securityGroups.values());
	}

	@Override
	public List<SecurityGroup> createSecurityGroup(SecurityGroupForCreate securityGroup) {
		return listSecurityGroups();
	}

	@Override
	public List<SecurityGroup> deleteSecurityGroups(Collection<Integer> ids) {
		return listSecurityGroups();
	}

	@Override
	public SecurityGroup createSecurityGroupRule(SecurityGroupRuleForCreate rule) {
		return new NovaSecurityGroup();
	}

	@Override
	public SecurityGroup deleteSecurityGroupRule(Collection<Integer> ids) {
		return new NovaSecurityGroup();
	}

	@Override
	public List<Tenant> listTenants(int start, int max) {
		return Lists.newArrayList(tenants.values());
	}

	@Override
	public List<Tenant> createTenant(Tenant tenant) {
		return listTenants(0, 10);
	}

	@Override
	public List<Tenant> deleteTenants(Collection<String> ids) {
		return listTenants(0, 10);
	}

	@Override
	public List<User> listUsers() {
		return Lists.newArrayList(users.values());
	}

	@Override
	public List<User> createUser(User user) {
		return listUsers();
	}

	@Override
	public List<User> deleteUser(Collection<String> ids) {
		return listUsers();
	}

	@Override
	public List<Role> listRoles() {
		return Lists.newArrayList(roles.values());
	}

	@Override
	public List<Role> createRole(Role role) {
		return listRoles();
	}

	@Override
	public List<Role> deleteRoles(Collection<String> ids) {
		return listRoles();
	}

	@Override
	public List<Service> listServices() {
		return Lists.newArrayList(services.values());
	}

	@Override
	public List<Service> createService(Service service) {
		return listServices();
	}

	@Override
	public List<Service> delteService(Collection<String> ids) {
		return listServices();
	}

	@Override
	public List<Endpoint> listEndpoints() {
		return Lists.newArrayList(endpoints.values());
	}

	@Override
	public List<Endpoint> createEndpoint(Endpoint endpoint) {
		return listEndpoints();
	}

	@Override
	public List<Endpoint> deleteEndpoint(Collection<String> ids) {
		return listEndpoints();
	}

}
