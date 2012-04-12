package org.cloudsherpa.ui.client;


import java.util.Collection;
import java.util.List;

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
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.User;
import org.openstack.model.images.Image;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("../ui/service")
public interface UIService extends RemoteService {
	
	public List<Server> listServers(int start, int max);
	
	public List<Server> createServer(ServerForCreate serverForCreate);
	
	public List<Server> deleteServers(Collection<String> ids);
	
	public Server executeServerAction(Collection<String> id, ServerAction action);
	
	public List<Flavor> listFlavors(int start, int max);
	
	public List<Flavor> createFlavor(Flavor flavor);
	
	public List<Flavor> deleteFlavors(Collection<String> ids);
	
	public List<Image> listImages(int starts, int max);
	
	public List<Image> createImage(Image image);
	
	public List<Image> deleteImages(Collection<String> ids);
	
	public List<FloatingIp> listFloatingIps();
	
	public List<FloatingIp> createFloatingIp();
	
	public List<FloatingIp> deleteFloatingIps(Collection<Integer> ids);
	
	public List<FloatingIp> associateFloatingIp(String ip, String serverId);
	
	public List<FloatingIp> disassociateFloatingIp(String ip);
	
	public List<Volume> listVolumes();
	
	public List<Volume> createVolume(VolumeForCreate volumeForCreate);
	
	public List<Volume> deleteVolumes(Collection<Integer> ids);
	
	public List<Volume> attachVolume();
	
	public List<Volume> detachVolumes(Integer id, String serverId);
	
	public List<Snapshot> listSnapshots();
	
	public List<Snapshot> createSnapshots(SnapshotForCreate snapshotForCreate);
	
	public List<Snapshot> deleteSnapshots(Collection<Integer> ids);
	
	public List<KeyPair> listKeyPairs();
	
	public KeyPair createKeyPair(String name);
	
	public List<KeyPair> deleteKeyPairs(Collection<String> names);
	
	public List<SecurityGroup> listSecurityGroups();
	
	public List<SecurityGroup> createSecurityGroup(SecurityGroupForCreate securityGroup);
	
	public List<SecurityGroup> deleteSecurityGroups(Collection<Integer> ids);
	
	public SecurityGroup createSecurityGroupRule(SecurityGroupRuleForCreate rule);
	
	public SecurityGroup deleteSecurityGroupRule(Collection<Integer> ids);
	
	//
	
	public List<Tenant> listTenants(int start, int max);
	
	public List<Tenant> createTenant(Tenant tenant);
	
	public List<Tenant> deleteTenants(Collection<String> ids);
	
	public List<User> listUsers();
	
	public List<User> createUser(User user);
	
	public List<User> deleteUser(Collection<String> ids);
	
	public List<Role> listRoles();
	
	public List<Role> createRole(Role role);
	
	public List<Role> deleteRoles(Collection<String> ids);
	
	public List<Service> listServices();
	
	public List<Service> createService(Service service);
	
	public List<Service> delteService(Collection<String> ids);
	
	public List<Endpoint> listEndpoints();
	
	public List<Endpoint> createEndpoint(Endpoint endpoint);
	
	public List<Endpoint> deleteEndpoint(Collection<String> ids);
}
